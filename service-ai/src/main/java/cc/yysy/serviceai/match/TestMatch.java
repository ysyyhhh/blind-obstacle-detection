package cc.yysy.serviceai.match;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;
import cc.yysy.serviceai.minio.MinioUtil;
import cc.yysy.serviceai.service.impl.AiServiceImpl;
import cc.yysy.utilscommon.utils.IOUtils;
import io.minio.messages.Item;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMatch {
    Logger logger = Logger.getLogger("TestMatch log");

    @Autowired
    private AiServiceImpl aiService;

    @Autowired
    private MinioUtil minioUtil;
    @Test
    public void testMatch() throws IOException {
        String dataSourceId = "5";
        //url = "http://127.0.0.1:9000/datasource-1/2023-04-15_17-10-13_125.16516-151.1561.jpg"

        List<Item> list =  minioUtil.listObjects("datasource-" + dataSourceId);
        for (Item item:list){

            String url = "http://127.0.0.1:9000/datasource-" + dataSourceId + "/" + item.objectName();
            logger.info("AiServiceImpl: " + url);
            aiService.dealRawFile(url, dataSourceId);
        }
        aiService.matchAll();

    }


}
