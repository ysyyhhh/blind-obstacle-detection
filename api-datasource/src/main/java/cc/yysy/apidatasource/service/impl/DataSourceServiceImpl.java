package cc.yysy.apidatasource.service.impl;


import cc.yysy.apidatasource.minio.MinioUtil;
import cc.yysy.utilscommon.constant.NameConstant;
import cc.yysy.utilscommon.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class DataSourceServiceImpl {

    static Logger logger = Logger.getLogger(DataSourceServiceImpl.class.getName());
    @Autowired
    MinioUtil minioUtil;


    public Result uploadRawImage(MultipartFile file, String dataSourceId) {
        String bucketName = NameConstant.DATA_SOURCE_BUCKET_PREFIX + dataSourceId;
        //判断是否有该数据源的bucket
        if (!minioUtil.bucketExists(bucketName)) {
            minioUtil.makeBucket(bucketName);
        }
        //提取文件名称
        String originalFilename = file.getOriginalFilename();
        logger.info("originalFilename:"+originalFilename);
        //originalFilename 自带了GPS信息
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException();
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        String nowDateLongStr = ft.format(date);
//        String objectName = nowDateLongStr + "/" + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = nowDateLongStr + "_" + originalFilename;

        logger.info(objectName);

        String fileUrl = minioUtil.upload(file,bucketName,objectName);
        if(fileUrl != null){
            //上传就处理
            uploadRawImageToRabbitMQ(fileUrl);
            return Result.success(fileUrl);
        }
        return Result.error("上传失败");
    }

    public void uploadRawImageToRabbitMQ(String fileUrl) {
        //上传后不立刻处理，否则时间顺序会有大问题。让datasource的定时任务去获取处理
//        logger.info("一上传成功就放入队列中处理？");
    }
}
