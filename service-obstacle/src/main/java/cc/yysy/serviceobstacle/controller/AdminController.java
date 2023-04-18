package cc.yysy.serviceobstacle.controller;

import cc.yysy.serviceobstacle.service.impl.ObstacleServiceImpl;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import cc.yysy.utilscommon.utils.ClassUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
@Component
public class AdminController {
    static final Logger logger = Logger.getLogger("UserController log");
    @Resource
    ObstacleServiceImpl obstacleService;

    @PostMapping("/addObstacle")
    public Result addObstacle(@RequestBody Map<String,Object> params){
        Obstacle newObstacle = new Obstacle();
        ClassUtils.MapToObject(newObstacle,params);
        return obstacleService.addObstacle(newObstacle);
    }

    @PostMapping("/deleteObstacle")
    public Result deleteObstacle(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        if(obstacleId == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.deleteObstacle(obstacleId);
    }

    @PostMapping("/updateObstacle")
    public Result updateObstacle(@RequestBody Map<String,Object> params){
        return obstacleService.updateObstacle(params);
    }

    @PostMapping("/assignResponsibility")
    public Result assignResponsibility(@RequestBody Map<String,Object> params){
        UserObstacleResponsibility userObstacleResponsibility = new UserObstacleResponsibility();
        ClassUtils.MapToObject(userObstacleResponsibility,params);
        return obstacleService.assignResponsibility(userObstacleResponsibility);
    }

    @PostMapping("/deleteResponsibility")
    public Result deleteResponsibility(@RequestBody Map<String,Object> params){
        UserObstacleResponsibility userObstacleResponsibility = new UserObstacleResponsibility();
        ClassUtils.MapToObject(userObstacleResponsibility,params);
        return obstacleService.deleteResponsibility(userObstacleResponsibility);
    }

    @PostMapping("/getUserListByObstacleId")
    public Result getUserListByObstacleId(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        if(obstacleId == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.getUserListByObstacleId(obstacleId);
    }

    @PostMapping("/getUserListByNotObstacleId")
    public Result getUserListByNotObstacleId(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        if(obstacleId == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.getUserListByNotObstacleId(obstacleId);
    }
}
