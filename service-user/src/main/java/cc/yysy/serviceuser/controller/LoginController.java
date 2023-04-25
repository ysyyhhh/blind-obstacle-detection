package cc.yysy.serviceuser.controller;


import cc.yysy.serviceuser.service.impl.UserServiceImpl;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/login")
@Component
public class LoginController {
    static final Logger logger = Logger.getLogger("LoginController log");
    @Autowired
    UserServiceImpl userService;


    /**
     * 注册1 密钥交付
     * @param params
     * @return 公钥
     */
    @PostMapping("/getPubKey")
    public Result getPubKey(@RequestBody Map<String,Object> params){
        String loginName = (String) params.get("loginName");
        if(loginName == null) return Result.error(ResultCode.PARAM_IS_BLANK);
        String pubKey = userService.getPubKey(loginName);
        if(pubKey == null) {
            return Result.error(ResultCode.SYSTEM_ERROR);
        }
        return Result.success(pubKey);
    }


    /**
     * 注册2 正式注册
     * @param sysUser
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody User sysUser){
//        User user = new User();
//        User sysUser = (User) ClassUtils.MapToObject(user,params);
        if(userService.signup(sysUser)){
            return Result.success("注册成功!");
        }
        return Result.error(-1,"注册失败!");
    }

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> params) throws UnsupportedEncodingException {
        logger.info("login");
        String loginName = (String) params.get("loginName");
        String password = (String) params.get("password");
        if (StringUtils.isNullOrEmpty(loginName) || StringUtils.isNullOrEmpty(password)) {
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        logger.info("loginName : " + loginName);
        logger.info("password : " + password);
//        System.out.println("password  :" + password);
        password = URLDecoder.decode(password,"UTF-8");
//        System.out.println("utf-8 : " + password);
        logger.info("utf-8 : " + password);
        return userService.login(loginName, password);
    }

}
