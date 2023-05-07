package cc.yysy.serviceai.service.impl;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;
import cc.yysy.serviceai.common.RadarResult;
import cc.yysy.serviceai.cv.impl.BlindServiceImpl;
import cc.yysy.serviceai.match.MatchService;
import cc.yysy.serviceai.match.impl.MatchServiceHungarianImpl;
import cc.yysy.serviceai.match.impl.MatchServiceSimpleImpl;
import cc.yysy.serviceai.minio.MinioUtil;
import cc.yysy.serviceai.rabbitmq.Sender;
import cc.yysy.serviceai.service.AiService;
import cc.yysy.serviceai.service.AreaService;
import cc.yysy.serviceai.service.ObstacleService;
import cc.yysy.serviceai.yolo.YoloService;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.result.Result;
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
    private BlindServiceImpl blindServiceImpl;


//    @Autowired
//    private MatchServiceSimpleImpl matchService;

    @Autowired
    private MatchServiceHungarianImpl matchService;


    @Autowired
    private AreaService areaService;

    @Autowired
    private ObstacleService obstacleService;

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
            if(detectObjectDtoList.size() == 0) {
//                logger.info("没有发现障碍物");
                return;
            }
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
            imageResult.setFileName(fileName);
            //存储结果
            if(!dataSourceIdToImageList.containsKey(dataSourceId)) {
                dataSourceIdToImageList.put(dataSourceId, new ArrayList<>());
            }
            List<ImageResult> list = dataSourceIdToImageList.get(dataSourceId);
            list.add(imageResult);
            dataSourceIdToImageList.put(dataSourceId, list);
            //删除原始文件
//            boolean deleteResult = minioUtil.removeFile(bucketName,fileName);
//            logger.info("删除原始文件结果：" + deleteResult);

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("处理文件失败");
        }

        //调用AI模型处理文件
//        minioUtil.downloadFile("miniofile",url,"D:\\test\\");
    }

    public List<DetectObjectDto>  detect(BufferedImage bufferedImage) throws IOException {
        //图像检测
        List<DetectObjectDto> detectObjectDtoList =  yoloService.detect(bufferedImage);
        //盲道分割 并 合并结果
        List<DetectObjectDto> result = blindServiceImpl.segBlindAndFilter(bufferedImage,detectObjectDtoList);
        //画一下
//        BufferedImage result1 = ImageUtils.drawDetectObjectDto(bufferedImage,result);
//        ImageIO.write(result1, "PNG", new File("D:\\out.jpg"));
        return result;
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
     * 定时任务，图像数据的关联处理
     * 每1分钟执行一次
     *
     */
    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?") //每1分钟执行一次
    public void matchAll() {
        logger.info("开始合并图像结果");
        //1.遍历每个数据源
        for (String dataSourceId : dataSourceIdToImageList.keySet()){
            matchResults(dataSourceId);
        }
    }

    private static final int THRESHOLD_RESULT = 5;
    private void matchResults(String dataSourceId){
        logger.info("开始合并图像结果：" + dataSourceId);
        //最后一帧距离现在超过1分钟 或 存在两帧之间的时间间隔超过1分钟
        List<ImageResult> imageResultList = dataSourceIdToImageList.get(dataSourceId);
        if(imageResultList == null || imageResultList.size() == 0) {
            return;
        }
        logger.info("图像结果数量：" + imageResultList.size());
        //按时间排序
        imageResultList.sort(new Comparator<ImageResult>() {
            @Override
            public int compare(ImageResult o1, ImageResult o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });

        logger.info("图像结果排序后：");
        for(ImageResult imageResult : imageResultList) {
            logger.info(imageResult.toString());
        }
        int gapIndex = findGap(imageResultList);
        if(gapIndex == -1 && System.currentTimeMillis() - imageResultList.get(imageResultList.size() - 1).getTime() < 60 * 1000) {
            return;
        }
        int end = gapIndex == -1 ? imageResultList.size() - 1 : gapIndex;
        logger.info("end = " + String.valueOf(end));

        List<DetectObjectDto> dtoList = matchService.match(imageResultList.subList(0,end+1));


        //保存结果
        logger.info("保存结果,有 " + dtoList.size() + " 个结果");


        for(DetectObjectDto dto : dtoList) {
//            if(dto.getBlindType() == 1) {
                logger.info("保存结果：" + dto.toString());
                save(dto.getClassName(), dataSourceId, imageResultList.get(0));
//            }
        }
        //在dataSourceIdToImageList中 删除结果
        imageResultList.removeAll(dtoList);

        dataSourceIdToImageList.put(dataSourceId, imageResultList);
    }

    private void save(String obstacleType, String dataSourceId, ImageResult imageResult) {
        //保存结果
        Map<String,Object> params = new HashMap<>();
//        Double longitude = (Double) params.get("longitude");
//        Double latitude = (Double) params.get("latitude");
        params.put("longitude",imageResult.getLongitude());
        params.put("latitude",imageResult.getLatitude());
        Result result = areaService.addAreaByGPS(params);

        if(result.getCode() == 1){
            Map<String,Object> param1 = new HashMap<>();
//            Obstacle obstacle = new Obstacle();
//            obstacle.setProcessingStatus(0);
//            obstacle.setImagePath(imageResult.getFileName());
//            obstacle.setType(obstacleType);
//            obstacle.setLocation(result.getData().toString());
//
            param1.put("processingStatus",0);
            param1.put("imagePath",imageResult.getFileName());
            param1.put("type",obstacleType);
            param1.put("location",result.getData().toString());
            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            param1.put("discoveryTime",ss.format(new Date(imageResult.getTime())) );
//            logger.info(imageResult.getTime() + " " + new Date(imageResult.getTime()));
            logger.info("保存障碍物信息：" + param1.toString());

            obstacleService.addObstacle(param1);
        }else{
            logger.info("获取区域信息失败");
        }
    }
    /**
     * 找到两帧之间的时间间隔超过1分钟的帧的下标
     * @param imageResultList
     * @return
     */
    private int findGap(List<ImageResult> imageResultList) {
        for (int i = 0; i < imageResultList.size() - 1; i++) {
            if(imageResultList.get(i + 1).getTime() - imageResultList.get(i).getTime() > 60 * 1000) {
                return i;
            }
        }
        return -1;
    }
}
