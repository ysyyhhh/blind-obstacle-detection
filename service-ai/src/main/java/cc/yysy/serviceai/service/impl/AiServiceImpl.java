package cc.yysy.serviceai.service.impl;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.minio.MinioUtil;
import cc.yysy.serviceai.rabbitmq.Sender;
import cc.yysy.serviceai.service.AiService;
import cc.yysy.serviceai.yolo.YoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
    /**
     * 调用AI模型处理原始文件
     * 处理结束后，将对应的url发送到rabbitmq的dealFile队列中
     *
     * @param url
     */
    public void dealRawFile(String url){
        logger.info("AiServiceImpl: " + url);

        //url = "http://127.0.0.1:9000/datasource-1/2023-04-15_17-10-13_125.16516-151.1561.jpg"
        logger.info("开始处理文件");
        String[] arr = url.split("/");
        String endUrl = arr[arr.length - 1];
        String[] endArr = endUrl.split("_");
        String date = endArr[0] + " " + endArr[1].replaceAll("-", ":");
        String[] gps = endArr[2].replace(".jpg", "").split("-");

        String bucketName = arr[arr.length - 2];
        String fileName = arr[arr.length - 1];
        InputStream inputStream =  minioUtil.getFileInputStream(bucketName,fileName);

        //inputStream 转 bufferedImage
        try {
            BufferedImage bufferedImage = inputStreamToBufferedImage(inputStream);
            List<DetectObjectDto>  detectObjectDtoList  = yoloService.detect(bufferedImage);
            logger.info("处理文件结束");

            //TODO: 把检测的结果存起来
            logger.info("这里要存储结果，存储的结果信息如下");
            logger.info("处理结果：" + detectObjectDtoList.toString());
            logger.info("发现时间：" + date);
            logger.info("发现位置：" + gps[0] + "," + gps[1]);

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

    private BufferedImage inputStreamToBufferedImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }
}
