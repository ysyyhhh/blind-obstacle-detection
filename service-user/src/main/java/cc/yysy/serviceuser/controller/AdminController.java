package cc.yysy.serviceuser.controller;

import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
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
@RequestMapping("/admin")
@Component
public class AdminController {
    static final Logger logger = Logger.getLogger("AdminController log");

    @Resource
    UserServiceImpl userService;

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody Map<String,Object> params){
        User newUser = new User();
        ClassUtils.MapToObject(newUser,params);
        return userService.updateUser(newUser);
    }

    @PostMapping("/modifyUserType")
    public Result modifyUserType(@RequestBody Map<String,Object> params){
        Integer userId = (Integer) params.get("userId");
        String userType = (String) params.get("userType");
        if (userId == null || userType == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        if(user.getId() == userId){
            return Result.error("不能撤销自己的身份");
        }
        return userService.modifyUserType(userId,userType);
    }

    @GetMapping("/getUserList")
    public Result getUserList(){
        return userService.getUserList();
    }


    @PostMapping("/getUserListByQuery")
    public Result getUserListByQuery(@RequestBody Map<String,Object> params){
        return userService.getUserListByQuery(params);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody Map<String,Object> params){
        User newUser = new User();
        ClassUtils.MapToObject(newUser,params);
        return userService.addUser(newUser);
    }
    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody Map<String,Object> params){
        Integer userId = (Integer) params.get("userId");
        if (userId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        if(user.getId() == userId){
            return Result.error("不能删除自己");
        }
        return userService.deleteUser(userId);
    }

}
