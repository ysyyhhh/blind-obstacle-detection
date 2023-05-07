package cc.yysy.serviceai.service;


import cc.yysy.serviceai.service.fallback.AreaServiceFallback;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

//指定用户调用哪个微服务
@FeignClient(
        value = "service-area",
        fallback = AreaServiceFallback.class
//        fallbackFactory = ProductServiceFallBackFactory.class
)
public interface AreaService {

    //指定请求的URL部分
    @PostMapping("/api/addAreaByGPS")
    public Result addAreaByGPS(@RequestBody Map<String,Object> params);
}
