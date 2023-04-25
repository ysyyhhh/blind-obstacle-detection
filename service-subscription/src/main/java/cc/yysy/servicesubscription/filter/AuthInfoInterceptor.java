package cc.yysy.servicesubscription.filter;

import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.utils.ThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Component
public class AuthInfoInterceptor implements HandlerInterceptor {
    static Logger logger = Logger.getLogger("AuthenticationInterceptor log");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userStr = request.getHeader(SystemConstant.HEADER_KEY_OF_USER);
        logger.info(">>>>>>>拦截到api相关请求头<<<<<<<<\n"+userStr);

        if(StringUtils.isNotEmpty(userStr)){
            String decodeUserStr = URLDecoder.decode(userStr, StandardCharsets.UTF_8.toString());
            logger.info("拦截后解码\n" + decodeUserStr);
            //直接搂下来，放到ThreadLocal中 后续直接从中获取
            ThreadLocalUtils.set(SystemConstant.HEADER_KEY_OF_USER,decodeUserStr);
        }
        logger.info("return true");
        return true;//注意 这里必须是true否则请求将就此终止。
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //移除app-user
        ThreadLocalUtils.remove(SystemConstant.HEADER_KEY_OF_USER);
        logger.info("移除请求头中的 "+SystemConstant.HEADER_KEY_OF_USER+"：" + ThreadLocalUtils.get("app-user"));
    }
}