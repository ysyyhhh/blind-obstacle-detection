package cc.yysy.serviceai.service.fallback;

import cc.yysy.serviceai.service.ObstacleService;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public class ObstacleServiceFallback implements ObstacleService {
    @Override
    public Result addObstacle(@RequestBody Map<String,Object> params) {
        return Result.error("调用失败，服务被降级");
    }
}
