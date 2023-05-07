package cc.yysy.serviceai.cv;

import cc.yysy.serviceai.cv.impl.BlindServiceImpl;
import cc.yysy.serviceai.service.impl.AiServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.*;
import org.opencv.imgproc.*;

import javax.imageio.ImageIO;

import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCV {

    @Autowired
    private BlindServiceImpl blindServiceImpl;

    @Autowired
    private AiServiceImpl aiService;
    @Test
    public void test() throws IOException {
        System.out.println("test");
        // 解决awt报错问题
        System.setProperty("java.awt.headless", "false");
        System.out.println(System.getProperty("java.library.path"));
        // 加载动态库
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java470.dll");
        System.load(url.getPath());
        // 读取图像

        Mat image = imread("E:\\raw_img\\gd_img\\740.jpg");
        if (image.empty()) {
            System.out.println("image is empty");
            return ;
//            throw new Exception("image is empty");
        }
//        imshow("Original Image", image);
        HSVBlind(image);
//        waitKey(0);
    }
    @Test
    public void testService() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(Files.newInputStream(Paths.get("E:\\raw_img\\gd_img\\742.jpg")));
//        blindService.bufferedImage2Mat(bufferedImage);
//        HSVBlind(blindService.bufferedImage2Mat(bufferedImage));
        aiService.detect(bufferedImage);
    }

    /**
     * 识别图片中的黄色盲道
     * @param img
     * @return
     */
    public List<Point> HSVBlind(Mat img){
//# 转换为HSV颜色空间
//                hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
        Mat hsv = img.clone();
        Imgproc.cvtColor(img, hsv, COLOR_BGR2HSV);

        Scalar lower_yellow = new Scalar(20, 45, 46);
        Scalar upper_yellow = new Scalar(30, 255, 255);

        Mat mask = new Mat(img.rows(), img.cols(), img.channels());
        Core.inRange(hsv, lower_yellow, upper_yellow, mask);

//    # 中值滤波
        Imgproc.medianBlur(mask, mask, 15);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(15, 15));
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, kernel);
//        imshow("morphologyEx Image", mask);
//     # python code 检测黄色区域的轮廓
      //    # 遍历所有轮廓，找到面积最大的四边形


        List<MatOfPoint> contours = new LinkedList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        double max_area = 0;
        Mat max_contour = null;
        for (int i = 0; i < contours.size(); i++) {
            double area = Imgproc.contourArea(contours.get(i));
            if (area > max_area) {
                max_area = area;
                max_contour = contours.get(i);
            }
        }
//    # 绘制面积最大的四边形
//
//       # 计算轮廓周长
//         # 进行多边形逼近
//     # 绘制轮廓
        List<Point> points = new LinkedList<>();
        if (max_contour != null) {
            MatOfPoint2f max_contour2f = new MatOfPoint2f(new MatOfPoint(max_contour).toArray());
            double perimeter = Imgproc.arcLength(max_contour2f, true);
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(max_contour2f, approx, 0.003 * perimeter, true);
            List<MatOfPoint> approxList = new LinkedList<>();
            approxList.add(new MatOfPoint(approx.toArray()));
//            approx.toString();
//            System.out.println(approx.toList().toString());
            points = approx.toList();
            Imgproc.drawContours(img, approxList, 0, new Scalar(0, 0, 255), 2);
//            imshow("Final Image", img);
        }
//        waitKey(0);
        return points;
    }


    /**
     * 判断矩形与多边形相交。
     * 通过判断矩形的四个顶点是否在多边形内部来判断。
     */
    public boolean isRectIntersectPoly(List<Point> rect, List<Point> poly) {
        for (Point p : rect) {
            if (isPointInPoly(p, poly)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断点是否在多边形内部
     */
    public boolean isPointInPoly(Point point, List<Point> poly) {
        int polyCorners = poly.size();
        int i, j = polyCorners - 1;
        boolean oddNodes = false;
        for (i = 0; i < polyCorners; i++) {
            if ((poly.get(i).y < point.y && poly.get(j).y >= point.y
                    || poly.get(j).y < point.y && poly.get(i).y >= point.y)
                    && (poly.get(i).x <= point.x || poly.get(j).x <= point.x)) {
                oddNodes ^= (poly.get(i).x + (point.y - poly.get(i).y) / (poly.get(j).y - poly.get(i).y) * (poly.get(j).x - poly.get(i).x) < point.x);
            }
            j = i;
        }
        return oddNodes;
    }
}
