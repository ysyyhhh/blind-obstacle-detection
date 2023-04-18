package cc.yysy.servicesubscription.controller;

import cc.yysy.servicesubscription.service.impl.SubscriptionServiceImpl;
import cc.yysy.utilscommon.entity.SubscriptionMessage;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ClassUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Component
public class AdminController {

    @Resource
    SubscriptionServiceImpl subscribeAreaService;

    @PostMapping("/addMessage")
    public Result addMessage(@RequestBody Map<String,Object> params){
        SubscriptionMessage subscriptionMessage = new SubscriptionMessage();
        ClassUtils.MapToObject(subscriptionMessage,params);
        return  subscribeAreaService.addMessage(subscriptionMessage);
    }

}
