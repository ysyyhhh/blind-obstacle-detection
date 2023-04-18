package cc.yysy.servicearea.controller;

import cc.yysy.servicearea.service.impl.AreaServiceImpl;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Component
public class ApiController {
    static final Logger logger = Logger.getLogger("ApiController log");
    @Resource
    AreaServiceImpl areaService;

    @PostMapping("/addAreaByGPS")
    public Result addAreaByGPS(@RequestBody Map<String,Object> params){
        Double longitude = (Double) params.get("longitude");
        Double latitude = (Double) params.get("latitude");
        if (longitude == null || latitude == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return areaService.addAreaByGPS(longitude,latitude);
    }




}
