package cc.yysy.utilscommon.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

public class FormatUtils {
    static Logger logger = Logger.getLogger("FormatUtils log");
    public static boolean judgeImageFileName(String fileName){
        String[] arrEnd = fileName.split("/");
        fileName = arrEnd[arrEnd.length-1];
        //判断文件名是否为空
        if(StringUtils.isBlank(fileName)){
            return false;
        }
        //如果不符合YYYY-MM-DD_HH-mm-ss_longitude_latitude.jpg的格式，就不上传
        String[] arr = fileName.split("_");
        if(arr.length != 4){
            logger.info("arr.length:"+arr.length);
            return false;
        }
        //判断时间是否正确
        String[] dateArr = arr[0].split("-");
        String[] timeArr = arr[1].split("-");
        if(dateArr.length != 3 || timeArr.length != 3){
            logger.info("dateArr.length:"+dateArr.length);
            logger.info("timeArr.length:"+timeArr.length);
            return false;
        }

        //判断经纬度是否正确
        String[] longitudeArr = arr[2].split("\\.");
        String[] latitudeAndSuffixArr = arr[3].split("\\.");
        if(longitudeArr.length != 2 || latitudeAndSuffixArr.length != 3){
            logger.info("longitudeArr.length:"+longitudeArr.length);
            logger.info("latitudeAndSuffixArr.length:"+latitudeAndSuffixArr.length);
            return false;
        }
        return true;
    }
}
