package cc.yysy.serviceobstacle.controller;

import cc.yysy.serviceobstacle.service.impl.ObstacleServiceImpl;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Component
public class ApiController {
    static Logger logger = Logger.getLogger("ApiController log");

    @Resource
    ObstacleServiceImpl obstacleService;

    @PostMapping("/addObstacle")
    public Result addObstacle(@RequestBody Map<String,Object> params){
        logger.info(params.toString());
        return obstacleService.addObstacle(params);
    }
}
