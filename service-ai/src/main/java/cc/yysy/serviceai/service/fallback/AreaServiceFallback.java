package cc.yysy.serviceai.service.fallback;

import cc.yysy.serviceai.service.AreaService;
import cc.yysy.utilscommon.result.Result;
import org.springframework.stereotype.Component;

import java.util.Map;

//容错类，实现feign所在的接口，并且去实现fegin的所有方法
//一旦feign远程调用出错，就进入当前类的同名方法
@Component
public class AreaServiceFallback implements AreaService {

    @Override
    public Result addAreaByGPS(Map<String, Object> params) {
        return Result.error("调用失败，服务被降级");
    }
}
