package cc.yysy.servicedatasource.controller;

import cc.yysy.servicedatasource.service.impl.DataSourceInfoServiceImpl;
import cc.yysy.servicedatasource.service.impl.DataSourceServiceImpl;
import cc.yysy.utilscommon.entity.DataSource;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import cc.yysy.utilscommon.utils.ClassUtils;
import cc.yysy.utilscommon.utils.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
@Component
public class AdminController {
    static final Logger logger = Logger.getLogger("UserController log");
    @Resource
    DataSourceInfoServiceImpl dataSourceInfoService;

    @Resource
    DataSourceServiceImpl dataSourceService;
    @GetMapping("/getDataSourceList")
    public Result getDataSourceList(){
        return dataSourceInfoService.getDataSourceList();
    }

    @PostMapping("/addDataSource")
    public Result addDataSource(@RequestBody Map<String,Object> params){
        DataSource dataSource = new DataSource();
        ClassUtils.MapToObject(dataSource,params);
        return dataSourceInfoService.addDataSource(dataSource);
    }

    @PostMapping("/deleteDataSource")
    public Result deleteDataSource(@RequestBody Map<String,Object> params){
        Integer dataSourceId = (Integer) params.get("dataSourceId");
        return dataSourceInfoService.deleteDataSource(dataSourceId);
    }

    @PostMapping("/updateDataSource")
    public Result updateDataSource(@RequestBody Map<String,Object> params){
        DataSource dataSource = new DataSource();
        ClassUtils.MapToObject(dataSource,params);
        return dataSourceInfoService.updateDataSource(dataSource);
    }

    @GetMapping("/image")
    public void getImage(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
        File file = new File(IOUtils.getRootPath() + "/images/raw/" + path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        response.getOutputStream().write(bytes);
    }

    @GetMapping("/rawImageList")
    public Result getRawImageList(@RequestParam("dataSourceId") String dataSourceId){
        return dataSourceService.getRawImageList(dataSourceId);
    }

    @PostMapping("/getDataSourceTokenById")
    public Result getDataSourceTokenById(@RequestBody Map<String,Object> params){
        Integer dataSourceId = (Integer) params.get("dataSourceId");
        if(dataSourceId == null){
            return Result.error(ResultCode.PARAM_IS_BLANK);
        }
        return dataSourceInfoService.getDataSourceTokenById(dataSourceId);
    }
}
