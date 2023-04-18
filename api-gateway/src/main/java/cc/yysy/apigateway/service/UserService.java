package cc.yysy.apigateway.service;

import cc.yysy.apigateway.service.fallback.UserServiceFallback;
import cc.yysy.utilscommon.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//指定用户调用哪个微服务
@FeignClient(
        value = "service-user",
        fallback = UserServiceFallback.class
//        fallbackFactory = ProductServiceFallBackFactory.class
)
public interface UserService {
    //指定请求的URL部分
    @RequestMapping("/api/getUser/{userPhone}")
    public User getUser(@PathVariable("userPhone") String userPhone);
}

