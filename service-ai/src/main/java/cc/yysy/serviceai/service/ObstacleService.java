package cc.yysy.serviceai.service;

import cc.yysy.serviceai.service.fallback.ObstacleServiceFallback;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.service.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        value = "service-obstacle",
        fallback = ObstacleServiceFallback.class
//        fallbackFactory = ProductServiceFallBackFactory.class
)
public interface ObstacleService {

    @PostMapping("/api/addObstacle")
    public Result addObstacle(@RequestBody Map<String,Object> params);
}
