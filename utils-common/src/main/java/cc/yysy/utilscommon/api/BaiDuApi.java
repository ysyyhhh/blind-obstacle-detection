package cc.yysy.utilscommon.api;

import cc.yysy.utilscommon.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BaiDuApi {
    private static String AK = "IIzT2daHXmWUCSLGGuLo6SIlSRN5G0SK";
    public static String []level = {"province","city","district","street","town"};
    static Logger logger = Logger.getLogger("BaiDuApi log");
    public static Map<String,Object> reverseGeocoding(Double latitude, Double longitude){
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak="+AK+"&output=json&coordtype=wgs84ll&extensions_town=true&location="+latitude+","+longitude;
        JSONObject response = HttpUtils.httpGet(url);
        JSONObject result = (JSONObject) response.get("result");
        JSONObject addressComponent = (JSONObject) result.get("addressComponent");
        String province = (String) addressComponent.get("province");
        String city = (String) addressComponent.get("city");
        String district = (String) addressComponent.get("district");
        String town = (String) addressComponent.get("town");
        String street = (String) addressComponent.get("street");
        String resultStr = province;
        Integer level = 0;
        if(city != null  && !city.equals("")){
            resultStr += "-"+city;
            level++;
            if(district != null && !district.equals("")){
                resultStr += "-"+district;
                level++;
                if(town != null  && !town.equals("")){
                    resultStr += "-"+town;
                    level++;
                    if(street != null && !street.equals("")){
                        resultStr += "-"+street;
                        level++;
                    }
                }
            }
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("resultStr",resultStr);
        returnMap.put("level",level);
//        logger.info(returnMap.toString());
        return returnMap;
    }

    public static void regionSearch(){
        String url = "https://api.map.baidu.com/api_region_search/v1/?keyword=北京&sub_admin=3&ak="+AK;
        JSONObject result = HttpUtils.httpGet(url);
        logger.info(result.toString());
    }

    public static String getLocation(String address) {
        String url = "https://api.map.baidu.com/geocoding/v3/?address="+address+"&output=json&ak="+AK;

        JSONObject result = HttpUtils.httpGet(url);
//        logger.info(result.toString());
        if(result.get("status").equals(0)){
            JSONObject location = (JSONObject) ((JSONObject) result.get("result")).get("location");
            return ""+location.get("lat")+","+location.get("lng");
        }
        return null;
    }
}
