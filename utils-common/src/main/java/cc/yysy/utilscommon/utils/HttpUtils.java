package cc.yysy.utilscommon.utils;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * 文件名：  ${file_name}
 * 版权：    Copyright by ljm
 * 描述：
 * 修改人：  HuamingChen
 * 修改时间：2018/10/24
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
public class HttpUtils {

    public static JSONObject httpGet(String url){
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.exchange(url, HttpMethod.GET,null,String.class).getBody();
        return JSONObject.parseObject(result);
    }

    public static JSONObject httpPost(String url, JSONObject object){
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.postForEntity(url,object,String.class).getBody();
        return JSONObject.parseObject(result);
    }

//    public static void main(String str[]){
//        //System.out.println(HttpTemplate.httpGet("http://localhost:8080/test"));
//        System.out.println(HttpTemplate.httpPost("http://localhost:8080/testPost1","ming"));
//    }
}
