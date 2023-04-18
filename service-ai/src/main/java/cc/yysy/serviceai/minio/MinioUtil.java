package cc.yysy.serviceai.minio;

import com.alibaba.nacos.common.util.UuidUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class MinioUtil {
    @Autowired
    private MinioConfig prop;

    @Resource
    private MinioClient minioClient;
//    @Autowired
//    private CodeService codeService;

    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            String policy = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetObject\"\n" +
                    "            ],\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": {\n" +
                    "                \"AWS\": [\n" +
                    "                    \"*\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\"Resource\": [\n" +
                    "                \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 删除存储bucket
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException();
        }
        String fileName = UuidUtils.generateUuid() + originalFilename.substring(originalFilename.lastIndexOf("."));
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calender = Calendar.getInstance();
        Date date = new Date();
        calender.setTime(date);
        String nowDateLongStr = ft.format(calender.getTime());
        String objectName = nowDateLongStr + "/" + fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(prop.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String upload(MultipartFile file,String bucketName,String objectName) {
        String fileUrl = null;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
            fileUrl = prop.getEndpoint() + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return fileUrl;
        }
        return fileUrl;
    }

    /**
     * 预览图片
     * @param fileName
     * @return
     */
    public String preview(String fileName){
        // 查看文件地址
        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder().bucket(prop.getBucketName()).object(fileName).method(Method.GET).build();
        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param res response
     * @return Boolean
     */
    public void download(String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(prop.getBucketName())
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件流
     *
     * @param fileName   文件名
     * @param bucketName 桶名（文件夹）
     * @return
     */
    public InputStream getFileInputStream(String bucketName,String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }
    /**
     * 删除
     * @param fileName
     * @param bucketName
     * @return
     * @throws Exception
     */
    public boolean removeFile(String bucketName,String fileName){
        try {
            minioClient.removeObject( RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * 删除
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String fileName){
        try {
            minioClient.removeObject( RemoveObjectArgs.builder().bucket(prop.getBucketName()).object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }



    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    public List<String> listUrl(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
        List<String> urlList = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
//                items.add(result.get());
                String fileUrl = prop.getEndpoint() + "/" + bucketName + "/" + result.get().objectName();
//                System.out.println(result.get().objectName() + "\t" + result.get().size() );
                urlList.add(fileUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return urlList;
    }

}
