package cc.yysy.apidatasource.controller;

import cc.yysy.apidatasource.minio.MinioConfig;
import cc.yysy.apidatasource.service.impl.DataSourceServiceImpl;
import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/raw")
@Component
public class DataSourceController {
    @Autowired
    private DataSourceServiceImpl dataSourceService;


    @Autowired
    private MinioConfig prop;

    @GetMapping("/test")
    public String test(){
        String dataSourceId = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_DATA_SOURCE);
        return dataSourceId;
    }

    @PostMapping("/image")
    public Result uploadRawImage(@RequestParam("file") MultipartFile file){
        String dataSourceId = ThreadLocalUtils.get(SystemConstant.HEADER_KEY_OF_DATA_SOURCE);
        return dataSourceService.uploadRawImage(file, dataSourceId);
    }

}
