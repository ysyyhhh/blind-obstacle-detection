package cc.yysy.servicearea.controller;

import cc.yysy.servicearea.service.impl.AreaServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
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
    AreaServiceImpl areaService;

    @PostMapping("/getMySubscribedAreaList")
    public Result getMySubscribedAreaList(@RequestBody Map<String,Object> params){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        params.put("userId",user.getId());
        if( params.get("page") == null || params.get("limit") == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return areaService.getUserSubscribedAreaList(params);
    }



    @PostMapping("/subscribeArea")
    public Result subscribeArea(@RequestBody Map<String,Object> params){
        Integer areaId = (Integer) params.get("areaId");
        if (areaId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        UserAreaSubscription userAreaSubscription = new UserAreaSubscription();
        userAreaSubscription.setAreaId(areaId);
        userAreaSubscription.setUserId(user.getId());
        return areaService.subscribeArea(userAreaSubscription);
    }
    @PostMapping("/unSubscribeArea")
    public Result unSubscribeArea(@RequestBody Map<String,Object> params){
        Integer areaId = (Integer) params.get("areaId");
        if (areaId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        UserAreaSubscription userAreaSubscription = new UserAreaSubscription();
        userAreaSubscription.setAreaId(areaId);
        userAreaSubscription.setUserId(user.getId());
        return areaService.unSubscribeArea(userAreaSubscription);
    }

    @PostMapping("/getMyAreaSubscriptionByAreaId")
    public Result getMyAreaSubscriptionByAreaId(@RequestBody Map<String,Object> params){
        Integer areaId = (Integer) params.get("areaId");
        if (areaId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        return areaService.getMyAreaSubscriptionByAreaId(user.getId(),areaId);
    }

    @GetMapping("/getOptions")
    public Result getOptions(){
        return areaService.getOptions();
    }


    @GetMapping("/getAreaTree")
    public Result getAreaTree(){
        return areaService.getAreaTree();
    }

    @GetMapping("/getLocation/{address}")
    public Result getLocation(@PathVariable String address){
        return areaService.getLocation(address);
    }


    @PostMapping("/getAreaListByLevel")
    public Result getAreaListByLevel(@RequestBody Map<String,Object> params){
        Integer level = (Integer) params.get("level");
        if (level == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return areaService.getAreaListByLevel(level);
    }
}
