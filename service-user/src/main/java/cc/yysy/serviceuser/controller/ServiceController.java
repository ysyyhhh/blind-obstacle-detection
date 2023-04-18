package cc.yysy.serviceuser.controller;


import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Component
public class ServiceController {
    static final Logger logger = Logger.getLogger("ServiceController log");
    @Resource
    UserServiceImpl userService;
    @GetMapping("/getUser/{userPhone}")
    public User getUser(@PathVariable("userPhone") String userPhone){
        logger.info("getUser " + userPhone);
//        System.out.println("getUser " + userPhone);
        return userService.getUser(userPhone);
    }
    @GetMapping("/test")
    public String test(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        logger.info(userStr);
        logger.info(user.getUsername());
        return "success!";
    }

//    @RequestMapping("/adminTest")
//    public String adminTest(){
//        return "success!";
//    }

    @GetMapping("testToken")
    public Result testToken(){
        return Result.success("token验证正确！！！");
    }

//    @GetMapping("adminTestToken")
//    public Result adminTestToken(){
//        return Result.success("admin token验证正确！！！");
//    }

    @GetMapping("/getUser")
    public Result getUser(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        return Result.success(user);
    }

    @GetMapping("/updateToken")
    public Result updateToken(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        return Result.success(userService.getToken(user) );
    }

//    @GetMapping("/getCoupleToken")
//    public Result getCoupleToken(){
//        logger.info("getCoupleToken");
////        console.log(getCoupleToken);
//        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
//        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
//
//        return Result.success(userService.getCoupleToken(user));
//    }
}
