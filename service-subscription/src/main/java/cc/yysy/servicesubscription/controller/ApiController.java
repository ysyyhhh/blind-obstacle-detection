package cc.yysy.servicesubscription.controller;


import cc.yysy.servicesubscription.service.impl.SubscriptionServiceImpl;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import org.springframework.stereotype.Component;
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
    SubscriptionServiceImpl subscriptionService;
    @PostMapping("addObstacleToArea")
    public Result addObstacleToArea(@RequestBody Map<String,Object> params){
        String areaFullName = (String) params.get("areaFullName");
        Integer obstacleId = (Integer) params.get("obstacleId");
        if (areaFullName == null || obstacleId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return subscriptionService.addObstacleToArea(areaFullName,obstacleId);
    }


}
