package cc.yysy.servicearea;

import cc.yysy.servicearea.Bean.MyAreaSubscription;
import cc.yysy.servicearea.service.impl.AreaServiceImpl;
import cc.yysy.utilscommon.entity.Area;
import cc.yysy.utilscommon.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AreaTest {
    static Logger logger = Logger.getLogger("AreaTest log");
    @Resource
    AreaServiceImpl areaService;

    @Test
    public void test() {
//        Result result = areaService.getUserSubscribedAreaList(5);
//        List<MyAreaSubscription> list = (List<MyAreaSubscription>) result.getData();
//        for(MyAreaSubscription myAreaSubscription : list) {
//            logger.info(myAreaSubscription.toString());
//        }
//        logger.info(result.toString());
       // System.out.println("test");
    }

    @Test
    public void addArea(){
        Double[][] gps = {{39.955451,116.329966},{39.951059,116.402536},{39.933698,116.429273},{40.00583,116.417925},
                {40.002666,116.356425},{39.989657,116.280239}};
        for (int i = 0; i < gps.length; i++) {
            areaService.addAreaByGPS(gps[i][0],gps[i][1]);
        }
    }
    @Test
    public void testTree(){
        areaService.getAreaTree();
    }
}
