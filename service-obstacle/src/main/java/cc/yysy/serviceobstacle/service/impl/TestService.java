package cc.yysy.serviceobstacle.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestService {
    @Resource
    ObstacleServiceImpl obstacleService;

    @Test
    public void test() {
        obstacleService.getOptions();
    }
}
