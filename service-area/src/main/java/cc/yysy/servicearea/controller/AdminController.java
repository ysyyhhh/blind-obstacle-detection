package cc.yysy.servicearea.controller;

import cc.yysy.servicearea.service.impl.AreaServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.Area;
import cc.yysy.utilscommon.entity.User;
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
    AreaServiceImpl areaService;
    @GetMapping("/getAreaList")
    public Result getAreaList(){
        return areaService.getAreaList();
    }

    @PostMapping("/updateArea")
    public Result updateArea(@RequestBody Map<String,Object> params){
        Area area = new Area();
        ClassUtils.MapToObject(area,params);
        return areaService.updateArea(area);
    }

    @PostMapping("/deleteArea")
    public Result deleteArea(@RequestBody Map<String,Object> params){
        Integer areaId = (Integer) params.get("areaId");
        if(areaId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return areaService.deleteArea(areaId);
    }

    @PostMapping("/insertArea")
    public Result insertArea(@RequestBody Map<String,Object> params){
        Area area = new Area();
        ClassUtils.MapToObject(area,params);
        return areaService.insertArea(area);
    }

    @GetMapping("/getUserSubscribedAreaList")
    public Result getUserSubscribedAreaList(@RequestBody Map<String,Object> params){
        if(params.get("userId") == null || params.get("page") == null || params.get("limit") == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return areaService.getUserSubscribedAreaList(params);
    }

}
