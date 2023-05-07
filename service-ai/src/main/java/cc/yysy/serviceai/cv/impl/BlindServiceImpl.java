package cc.yysy.serviceai.cv.impl;


import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.cv.BlindService;
import org.springframework.stereotype.Service;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.objdetect.CascadeClassifier;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;
@Service
public class BlindServiceImpl implements BlindService {
    Logger logger = Logger.getLogger("BlindService");


    @PostConstruct
    public void init(){
        // 解决awt报错问题
        System.setProperty("java.awt.headless", "false");
        System.out.println(System.getProperty("java.library.path"));
        // 加载动态库
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java470.dll");
        System.load(url.getPath());
    }
    /**
     * 识别图片中的黄色盲道并把障碍物过滤掉
     * @param img
     * @return
     */
    public List<DetectObjectDto> segBlindAndFilter(BufferedImage img,List<DetectObjectDto> detectObjectDtos){
        logger.info("segBlindAndFilter");
        logger.info(detectObjectDtos.toString());
        Mat mat = bufferedImage2Mat(img);
        List<Point> points = HSVBlind(mat);
        List<DetectObjectDto> result = new LinkedList<>();
        for (DetectObjectDto detect : detectObjectDtos) {
            DetectObjectDto detectObjectDto = new DetectObjectDto();
            detectObjectDto.setX(detect.getX()*img.getWidth());
            detectObjectDto.setY(detect.getY()*img.getHeight());
            detectObjectDto.setWidth(detect.getWidth()*img.getWidth());
            detectObjectDto.setHeight(detect.getHeight()*img.getHeight());
            List<Point> points1 = new LinkedList<>();
            points1.add(new Point(detectObjectDto.getX(),detectObjectDto.getY()));
            points1.add(new Point(detectObjectDto.getX()+detectObjectDto.getWidth(),detectObjectDto.getY()));
            points1.add(new Point(detectObjectDto.getX()+detectObjectDto.getWidth(),detectObjectDto.getY()+detectObjectDto.getHeight()));
            points1.add(new Point(detectObjectDto.getX(),detectObjectDto.getY()+detectObjectDto.getHeight()));
            if(isRectIntersectPoly(points1,points)){
                result.add(detect);
            }
        }
        logger.info(result.toString());
        return result;
    }

    /**
     * 识别图片中的黄色盲道
     * @param img
     * @return
     */
    public List<Point> HSVBlind(Mat img){
//  转换为HSV颜色空间
//  hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
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
    private boolean isRectIntersectPoly(List<Point> rect, List<Point> poly) {
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
    private boolean isPointInPoly(Point point, List<Point> poly) {
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


    /**
     * BufferedImage转Mat
     * @param im
     * @return
     */
//    public Mat bufferedImage2Mat(BufferedImage image){
//        BufferedImage bimg1 = image;
//
//        bimg1 = converttoRGBformat(bimg1); // BufferedImage convert to TYPE_3BYTE_BGR(RGB格式)
//
//        byte[] image_rgb = (byte[]) bimg1.getData().getDataElements(0, 0, bimg1.getWidth(), bimg1.getHeight(), null);
//
//        Mat bimg1_mat = new Mat(bimg1.getHeight(), bimg1.getWidth(), CvType.CV_8UC3);
//
////        bimg1_mat.data().put(image_rgb);
//        bimg1_mat.put(0, 0, image_rgb);
//        cvtColor(bimg1_mat, bimg1_mat, COLOR_RGB2BGR); // RGB转BGR
//        return bimg1_mat;
//    }
    // Convert image to Mat
    public Mat bufferedImage2Mat(BufferedImage im) {
        // Convert INT to BYTE
        //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
        byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer())
                .getData();

        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
        // Fill Matrix with image values
        image.put(0, 0, pixels);

        return image;

    }
    /**
     * BufferedImage均转为TYPE_3BYTE_BGR，RGB格式
     *
     * @param input 未知格式BufferedImage图片
     * @return TYPE_3BYTE_BGR格式的BufferedImage图片
     */
    public static BufferedImage converttoRGBformat(BufferedImage input)
    {
        if (null == input)
        {
            throw new NullPointerException("BufferedImage input can not be null!");
        }
        if (BufferedImage.TYPE_3BYTE_BGR != input.getType())
        {
            BufferedImage input_rgb = new BufferedImage(input.getWidth(), input.getHeight(),
                    BufferedImage.TYPE_3BYTE_BGR);
            new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_sRGB), null).filter(input, input_rgb);
            return input_rgb;
        } else
        {
            return input;
        }
    }
}
