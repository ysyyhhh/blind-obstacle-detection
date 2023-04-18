package cc.yysy.servicedatasource.service.impl;

import cc.yysy.servicedatasource.mapper.DataSourceMapper;
import cc.yysy.servicedatasource.minio.MinioUtil;
import cc.yysy.servicedatasource.rabbitmq.Sender;
import cc.yysy.servicedatasource.utils.RedisSetUtil;
import cc.yysy.utilscommon.constant.NameConstant;
import cc.yysy.utilscommon.entity.DataSource;
import cc.yysy.utilscommon.result.Result;
import io.minio.PutObjectArgs;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class DataSourceServiceImpl {
    static Logger logger = Logger.getLogger(DataSourceServiceImpl.class.getName());
    @Autowired
    private MinioUtil minioUtil;
    @Resource
    DataSourceMapper dataSourceMapper;

    @Autowired
    private RedisSetUtil redisSetUtil;

    @Autowired
    private Sender sender;
    private static final String ENQUEUE_FILE = "enqueue";
    public Result getRawImageList(String dataSourceId) {
        String bucketName = NameConstant.DATA_SOURCE_BUCKET_PREFIX + dataSourceId;
        //判断是否有该数据源的bucket
        if (!minioUtil.bucketExists(bucketName)) {
            minioUtil.makeBucket(bucketName);
        }
        List<String> list = minioUtil.listUrl(bucketName);
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(int i = 0; i < Math.min(list.size(),10); i++){
            Map<String,Object> result = new HashMap<>();
            String url = list.get(i);
            String[] arr = url.split("/");
            String endUrl = arr[arr.length - 1];
            String[] endArr = endUrl.split("_");
            String date = endArr[0] + " " + endArr[1].replaceAll("-", ":");
            String[] gps = endArr[2].replace(".jpg", "").split("-");
            result.put("url",url);
            result.put("date",date);
            result.put("gps",gps);
            result.put("status","等待处理");
            resultList.add(result);
        }
        return Result.success(resultList);
    }



    /**
     * 定时任务，从服务启动开始每隔5分钟执行一次
     * 将每个datasource存在minio中的图片，上传到rabbitmq的rawFile队列中，并用redis的Set做已经上传的标记
     */
    @PostConstruct
    @Scheduled(cron = "0 0/5 * * * ?")
    public void uploadRawImageToRabbitMQ() {
        logger.info("开始上传图片到rabbitmq");
        List<DataSource> list = dataSourceMapper.getDataSourceList();
        for(DataSource dataSource : list){
            String bucketName = NameConstant.DATA_SOURCE_BUCKET_PREFIX + dataSource.getId();
            //判断是否有该数据源的bucket
            if (!minioUtil.bucketExists(bucketName)) {
                minioUtil.makeBucket(bucketName);
            }
            List<String> itemList = minioUtil.listUrl(bucketName);
            if(itemList.isEmpty()) continue;
            for(String url : itemList){
                if(redisSetUtil.isMember(ENQUEUE_FILE,url)){
                    //如果已经上传过了，就跳过
                    continue;
                }
                sender.send(url);
                redisSetUtil.add(ENQUEUE_FILE,url,60);
            }
        }
    }


}
