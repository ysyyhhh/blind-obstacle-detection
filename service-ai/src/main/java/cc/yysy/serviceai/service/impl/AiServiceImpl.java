package cc.yysy.serviceai.service.impl;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;
import cc.yysy.serviceai.common.RadarResult;
import cc.yysy.serviceai.minio.MinioUtil;
import cc.yysy.serviceai.rabbitmq.Sender;
import cc.yysy.serviceai.service.AiService;
import cc.yysy.serviceai.yolo.YoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class AiServiceImpl implements AiService {
    static Logger logger = Logger.getLogger("AiServiceImpl log");

    @Autowired
    private Sender sender;

    @Autowired
    private YoloService yoloService;

    @Autowired
    private MinioUtil minioUtil;

    static Map<String,List<RadarResult> > dataSourceIdToRadarList = new HashMap<>();

    static Map<String,List<ImageResult>> dataSourceIdToImageList = new HashMap<>();

    /**
     * 调用AI模型处理原始文件
     * 处理结束后，将对应的url发送到rabbitmq的dealFile队列中
     *
     * @param url
     */
    public void dealRawFile(String url,String dataSourceId){

        logger.info("AiServiceImpl: " + url);
        //url = "http://127.0.0.1:9000/datasource-1/2023-04-15_17-10-13_125.16516-151.1561.jpg"
        logger.info("开始处理文件");
        String[] arr = url.split("/");
        String endUrl = arr[arr.length - 1];
        String[] endArr = endUrl.split("_");
        String dateStr = endArr[0] + "_" + endArr[1];
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = null;
        try {
            date = ft.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] gps = {endArr[2],endArr[3].replace(".jpg", "")};

        String bucketName = arr[arr.length - 2];
        String fileName = arr[arr.length - 1];
        InputStream inputStream =  minioUtil.getFileInputStream(bucketName,fileName);

        //inputStream 转 bufferedImage
        try {
            BufferedImage bufferedImage = inputStreamToBufferedImage(inputStream);
            List<DetectObjectDto>  detectObjectDtoList  = detect(bufferedImage);
            logger.info("处理文件结束");
            //得到每一帧的图像处理结果

            //把每一帧的图像结果序列合并起来，识别是否存在对应障碍物
            logger.info("这里要存储结果，存储的结果信息如下");
            logger.info("处理结果：" + detectObjectDtoList.toString());
            logger.info("发现时间：" + date);
            logger.info("发现位置：" + gps[0] + "," + gps[1]);
            ImageResult imageResult = new ImageResult();
            imageResult.setDtoList(detectObjectDtoList);
            imageResult.setTime(date.getTime());
            imageResult.setLongitude(Double.parseDouble(gps[0]));
            imageResult.setLatitude(Double.parseDouble(gps[1]));
            //存储结果
            if(!dataSourceIdToImageList.containsKey(dataSourceId)) {
                dataSourceIdToImageList.put(dataSourceId, new ArrayList<>());
            }
            List<ImageResult> list = dataSourceIdToImageList.get(dataSourceId);
            list.add(imageResult);

            //删除原始文件
            boolean deleteResult = minioUtil.removeFile(bucketName,fileName);
            logger.info("删除原始文件结果：" + deleteResult);

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("处理文件失败");
        }

        //调用AI模型处理文件
//        minioUtil.downloadFile("miniofile",url,"D:\\test\\");
    }

    private List<DetectObjectDto>  detect(BufferedImage bufferedImage){
        //TODO 盲道分割

        //TODO 图像检测

        //TODO 合并结果

        //调用AI模型处理文件
        return yoloService.detect(bufferedImage);
    }

    private BufferedImage inputStreamToBufferedImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    /**
     * 雷达数据从这里接收,添加到对应的list中
     * @param date
     * @param num
     * @param dataSourceId
     */
    public void dealRadar(Date date, Integer num, String dataSourceId) {
        Long time = date.getTime();
        if(!dataSourceIdToRadarList.containsKey(dataSourceId)) {
            dataSourceIdToRadarList.put(dataSourceId, new ArrayList<>());
        }
        List<RadarResult> list = dataSourceIdToRadarList.get(dataSourceId);
        list.add(new RadarResult(time, num));
    }


    /**
     * 定时任务，进行雷达和图像数据的融合
     * 每1分钟执行一次
     *
     */
    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?") //每1分钟执行一次
    public void mergeRadarAndImage() {
        logger.info("开始融合雷达和图像数据");
        //TODO 融合
        //1.遍历每个数据源

//        for (String dataSourceId : dataSourceIdToTimeList.keySet()) {
//            mergeRadarAndImageSingle(dataSourceId);
//        }
    }
    private static final int THRESHOLD_RESULT = 5;
    private void mergeRadarAndImageSingle(String dataSourceId){
        logger.info("开始融合雷达和图像数据，数据源：" + dataSourceId);
        //图像结果大于一个数量才开始处理
        List<ImageResult> imageResultList = dataSourceIdToImageList.get(dataSourceId);
        if(imageResultList.size() < THRESHOLD_RESULT) {
            return;
        }

        //2.遍历阈值一半的图像结果
        int threshold = THRESHOLD_RESULT / 2;
        for (int i = 0; i < threshold; i++) {
            ImageResult imageResult = imageResultList.get(i);
            //3.遍历雷达结果
            List<RadarResult> radarResultList = dataSourceIdToRadarList.get(dataSourceId);

            //            for (RadarResult radarResult : radarResultList) {
//                //4.判断时间是否在阈值范围内
//                if(Math.abs(imageResult.getTime() - radarResult.getTime()) < 1000 * 60 * 5) {
//                    //5.判断雷达结果是否大于阈值
//                    if(radarResult.getNum() > 5) {
//                        //6.发送消息
//                        logger.info("发送消息");
//                        sender.send("dealFile", imageResult);
//                    }
//                }
//            }
        }

    }
}
