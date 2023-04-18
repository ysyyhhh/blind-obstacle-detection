package cc.yysy.serviceobstacle.controller;

import cc.yysy.serviceobstacle.Bean.ListQuery;
import cc.yysy.serviceobstacle.service.ObstacleService;
import cc.yysy.serviceobstacle.service.impl.ObstacleServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import cc.yysy.utilscommon.utils.ClassUtils;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@Component
public class UserController {
    static final Logger logger = Logger.getLogger("UserController log");
    @Resource
    ObstacleServiceImpl obstacleService;

    @PostMapping("/updateObstacleProcessingStatus")
    public Result updateObstacleProcessingStatus(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        Integer newStatus = (Integer) params.get("newStatus");
        if(obstacleId == null || newStatus == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        if(user == null) return Result.error("用户不存在");
        Obstacle obstacle = obstacleService.getObstacle(obstacleId);
        if(obstacle == null) return Result.error("障碍物不存在");
        if(obstacle.getProcessingStatus() == newStatus) return Result.error("障碍物状态未改变");
        if(obstacle.getProcessingStatus() == 1){
            //撤销操作
            logger.info(user.toString());
            if(!user.getUserType().matches("admin") && obstacle.getProcessorId() != user.getId()) return Result.error("你不是障碍物的处理人 / 没有管理员权限");
        }
        logger.info("user: " + user.getId() + " obstacle: " + obstacleId + " newStatus: " + newStatus);
        return obstacleService.updateObstacleProcessingStatus(user.getId(),obstacle,newStatus);
    }

    /**
     * 用于区域管理页面
     */
    @PostMapping("/getObstaclesByFullName")
    public Result getObstaclesByFullName(@RequestBody Map<String,Object> params){
        String fullName = (String) params.get("areaFullName");
        if(fullName == null) fullName = "%";
        return obstacleService.getObstaclesByFullName(fullName);
    }

    @PostMapping("/getObstacleDetail")
    public Result getObstacleDetail(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        if(obstacleId == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.getObstacleDetail(obstacleId);
    }

    /**
listQuery: {
        page: 1,
        limit: 20,
        area: undefined,
        type: undefined,
        processingStatus: undefined,
        processingStartTime: undefined,
        processingEndTime: undefined,
        discoveryStartTime: undefined,
        discoveryEndTime: undefined,
        processor: undefined
      },

      return list,total
     **/
    @PostMapping("/getObstacleList")
    public Result getObstacleList(@RequestBody Map<String,Object> params){
        ListQuery listQuery = new ListQuery();
        ClassUtils.MapToObject(listQuery,params);
        if(listQuery.getPage() == null || listQuery.getLimit() == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        if(listQuery.getProcessingStartTime() != null && listQuery.getProcessingEndTime() != null){
            if(listQuery.getProcessingStartTime().compareTo(listQuery.getProcessingEndTime()) > 0){
                return Result.error("处理时间区间错误");
            }
        }
        if(listQuery.getDiscoveryStartTime() != null && listQuery.getDiscoveryEndTime() != null){
            if(listQuery.getDiscoveryStartTime().compareTo(listQuery.getDiscoveryEndTime()) > 0){
                return Result.error("发现时间区间错误");
            }
        }

        return obstacleService.getObstacleList(listQuery);
    }


    @GetMapping("/getOptions")
    public Result getOptions(){
        return obstacleService.getOptions();
    }

    @PostMapping("/getObstacleListByArea")
    public Result getObstacleListByArea(@RequestBody Map<String,Object> params){
        String areaFullName = (String) params.get("areaFullName");
        if(areaFullName == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.getObstacleListByArea(areaFullName);
    }

    @PostMapping("/getResponsibilityByobstacleId")
    public Result getResponsibilityByobstacleId(@RequestBody Map<String,Object> params){
        Integer obstacleId = (Integer) params.get("obstacleId");
        if(obstacleId == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        return obstacleService.getResponsibilityByobstacleId(obstacleId);
    }
}
