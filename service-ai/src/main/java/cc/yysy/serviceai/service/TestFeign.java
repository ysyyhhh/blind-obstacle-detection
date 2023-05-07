package cc.yysy.serviceai.service;

import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class TestFeign {

    @Autowired
    private AreaService areaService;
    @Test
    public void testFeign() {
        Map<String,Object> map = new HashMap<>();
        map.put("lat", 30.0);
        map.put("lng", 120.0);
        Result result =  areaService.addAreaByGPS(map);
        System.out.println(result);

//        Obstacle obstacle = new Obstacle();
//        obstacle.setProcessingStatus(0);
//        obstacle.setImagePath(imageResult.getFileName());
//        obstacle.setType(obstacleType);
//        obstacle.setLocation(result.getData().toString());
//        obstacleService.addObstacle(obstacle);

    }

}
