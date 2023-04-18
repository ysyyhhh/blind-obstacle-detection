package cc.yysy.servicedatasource.controller;

import cc.yysy.servicedatasource.minio.MinioConfig;
import cc.yysy.servicedatasource.minio.MinioUtil;
import cc.yysy.utilscommon.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/minio")
@Component
public class MinioController {
    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private MinioConfig prop;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (objectName != null) {
            return Result.success(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName);
        }
        return Result.error("上传失败");
    }

}