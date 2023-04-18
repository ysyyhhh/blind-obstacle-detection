package cc.yysy.servicesubscription.controller;

import cc.yysy.servicesubscription.service.impl.SubscriptionServiceImpl;
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
@RestController
@RequestMapping("/user")
@Component
public class UserController {

    @Resource
    SubscriptionServiceImpl subscribeAreaService;

    @GetMapping("/getUnReadMessageList")
    public Result getUnReadMessageList(){
        String userStr = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_USER);
        User user = (User) JSONObject.toJavaObject( JSONObject.parseObject(userStr),User.class);
        return subscribeAreaService.getUnReadMessageList(user.getId());
    }

}
