package cc.yysy.serviceuser.controller;


import cc.yysy.serviceuser.service.impl.UserServiceImpl;
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
    UserServiceImpl userService;


    @GetMapping("/getUser")
    public Result getUser(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
//        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
//        User user = JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
//        return Result.success(user);
        logger.info(user.toString());
        return Result.success(user);
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody Map<String,Object> params){
        User newUser = new User();
        ClassUtils.MapToObject(newUser,params);
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);

        //普通用户不能修改其他用户，也不能修改自己的权限
        if(!newUser.getPhoneNumber().matches(user.getPhoneNumber())||!newUser.getUserType().matches(user.getUserType())){
            return Result.error(ResultCode.USER_NOT_ADDMISSION);
        }
        return userService.updateUser(newUser);
    }

    @PostMapping("/updateUserPassword")
    public Result updateUserPassword(@RequestBody Map<String,Object> params){
        String oldPassword = (String) params.get("oldPassword");
        String newPassword = (String) params.get("newPassword");
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        return userService.updateUserPassword(user,oldPassword,newPassword);
    }

}
