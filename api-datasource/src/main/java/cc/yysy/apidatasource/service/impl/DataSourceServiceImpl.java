package cc.yysy.apidatasource.service.impl;


import cc.yysy.apidatasource.minio.MinioUtil;
import cc.yysy.apidatasource.rabbitmq.Sender;
import cc.yysy.utilscommon.constant.NameConstant;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.FormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class DataSourceServiceImpl {

    static Logger logger = Logger.getLogger(DataSourceServiceImpl.class.getName());
    @Autowired
    MinioUtil minioUtil;

    @Autowired
    Sender sender;



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
        //originalFilename = "2020-01-01_00-00-00_1.1_2.2.jpg"
        if(!FormatUtils.judgeImageFileName(originalFilename)){
            return Result.error("文件名不符合规范");
        }
        //时间应该由数据源提供
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        Date date = new Date();
//        String nowDateLongStr = ft.format(date);
//        String objectName = nowDateLongStr + "/" + originalFilename.substring(originalFilename.lastIndexOf("."));
//        String objectName = nowDateLongStr + "_" + originalFilename;
        String objectName = originalFilename;
        logger.info(objectName);
        String fileUrl = minioUtil.upload(file,bucketName,objectName);
        if(fileUrl != null){
            //上传就处理
            uploadRawImageToRabbitMQ(fileUrl, dataSourceId);
            return Result.success(fileUrl);
        }
        return Result.error("上传失败");
    }

    public void uploadRawImageToRabbitMQ(String fileUrl, String dataSourceId) {
        Map<String,Object> params = new HashMap<>();
//        params.put("url",fileUrl);
//        params.put("dataSourceId",dataSourceId);
//        sender.send(params);
        //上传后不立刻处理，否则时间顺序会有大问题。让datasource的定时任务去获取处理
//        logger.info("一上传成功就放入队列中处理？");
    }


    public Result uploadRadar(String dateStr, Integer num, String dataSourceId) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        //根据SimpleDateFormat 得到Date
        Date date = null;
        try {
            date = ft.parse(dateStr);
            Map<String,Object> params = new HashMap<>();
            params.put("date",date);
            params.put("num",num);
            params.put("dataSourceId",dataSourceId);
            sender.send(params);
            return Result.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }
}
