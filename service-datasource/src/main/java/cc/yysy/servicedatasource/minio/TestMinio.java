package cc.yysy.servicedatasource.minio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMinio {
    static Logger logger = Logger.getLogger("TestMinio");
    @Resource
    MinioUtil minioUtil;
    @Test
    public void test() throws Exception {
        boolean result =  minioUtil.bucketExists("miniofile");
        logger.info("result:"+result);
    }

    @Test
    public void getList(){
//        minioUtil.listObjectName("datasource-1");
    }
//    public void createBucket(MinioClient minioClient) throws Exception{
//        boolean exists = minioClient
//                .bucketExists(BucketExistsArgs.builder().bucket("miniofile").build());
//        if (exists) {
//            return;
//        }
//        minioClient.makeBucket(MakeBucketArgs.builder().bucket("miniofile").build());
//    }
}
