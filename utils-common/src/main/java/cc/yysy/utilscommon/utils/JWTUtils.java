package cc.yysy.utilscommon.utils;


import cc.yysy.utilscommon.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.logging.Logger;


@Component
public class JWTUtils {

    private static Logger logger = Logger.getLogger("JWTUtils log");

    private static String name = "workhours";

    private static Long expiresSecond = Long.parseLong("720000000");

//    audience: # 代表这个JWT的接收对象,存入audience
//    clientId: REDACTED # 密钥, 经过Base64加密, 可自行替换
//    base64Secret: REDACTED # JWT的签发主体，存入issuer
//    name: workhours
//    expiresSecond: 72000000 # 过期时间，时间戳 120分钟
    public static JWTUtils jwtUtils;

    @PostConstruct
    public void init(){
        jwtUtils=this;
    }
    /**
     * 设置用户的token
     * 私有，只允许被getToken调用
     * @param user
     * @return
     */
    private static String setToken(User user){
        String token="";
        Date now = new Date();
        logger.info(expiresSecond.toString());
        logger.info(user.toString());
        logger.info(user.getPhoneNumber());
        Date expiresTime = new Date(now.getTime() + expiresSecond);
        token= JWT.create()
                .withAudience(user.getPhoneNumber())
                .withIssuer(name)
                .withIssuedAt(now)
                .withExpiresAt(expiresTime)
                .sign(Algorithm.HMAC256(user.getPassword()));

        logger.info(now.toString());
        logger.info(expiresTime.toString());
//        redisUtils.set(user.getUserPhone(),token,expiresSecond/2/1000);
        return token;
    }

    /**
     * 获取用户的Token
     * @param user
     * @return     */
    public static String getToken(User user) {
        logger.info("getToken");

//        if(redisUtils.hasKey(user.user_id)){
//            return (String) redisUtils.get(user.user_id);
//        }
        return setToken(user);
    }

    /**
     * 验证该token 和 用户的区别
     * @param token
     * @param user
     * @return
     */
    public static boolean verify(String token, User user){
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token不合法！");
        }
        return true;
    }


    /********datasource*********/
    public static String getDatasourceToken(Integer datasourceId){
        String token = JWT.create()
                .withAudience(datasourceId.toString())
                .withIssuer(name)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256("datasource"));
        return token;
    }

    public static Integer verifyDatasourceToken(String token){
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("datasource")).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token不合法！");
        }
        return Integer.parseInt(JWT.decode(token).getAudience().get(0));
    }

}
