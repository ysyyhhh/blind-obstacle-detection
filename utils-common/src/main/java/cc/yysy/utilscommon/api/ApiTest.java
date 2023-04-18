package cc.yysy.utilscommon.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiTest {
    static Logger logger = Logger.getLogger("BaiDuApi log");
    @Test
    public void testReverseGeocoding(){
        String resultStr = "";
        Integer level = 0;
        Map<String,Object> returnMap = BaiDuApi.reverseGeocoding(38.76623,116.43213);
        resultStr = (String) returnMap.get("resultStr");
        level = (Integer) returnMap.get("level");
        System.out.println(resultStr);
        System.out.println(level);
    }

    @Test
    public void testRegionSearch(){
        BaiDuApi.regionSearch();
    }

    @Test
    public void testGetLocation(){
        logger.info(BaiDuApi.getLocation("北京市海淀区上地十街10号"));
    }
}
