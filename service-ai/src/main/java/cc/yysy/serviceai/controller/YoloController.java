package cc.yysy.serviceai.controller;

import ai.djl.modality.cv.output.DetectedObjects;
import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.yolo.YoloService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TinyThing
 * @see <a href="https://www.jianshu.com/u/aba665c4151f">简书TinyThing</a>
 * @since 2022/3/19 13:38
 */
@RestController
@RequestMapping("/yolo")
@Component
public class YoloController {

    @Resource
    YoloService yoloService;

    @PostMapping("/image")
//    @ApiOperation("图片对象检测，返回结果图片")
    public void ocr(@RequestPart MultipartFile file, HttpServletResponse response) throws IOException {

        BufferedImage image = ImageIO.read(file.getInputStream());
        BufferedImage result = yoloService.getResultImage(image);

        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(result, "PNG", os);
        os.flush();
    }

    @PostMapping("/upload")
//    @ApiOperation("图片对象检测，返回结果对象")
    public List<DetectObjectDto> ocr(@RequestPart MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
//        DetectedObjects result = yoloService.detect(image);

        return yoloService.detect(image);
    }



}
