package cc.yysy.apigateway.security;


import cc.yysy.utilscommon.constant.SystemConstant;
import cc.yysy.utilscommon.entity.User;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.logging.Logger;

@Setter
@Getter
@Component
@ConfigurationProperties("org.my.jwt")
public class JWTAuthFilter implements GlobalFilter, Ordered {
    static Logger logger = Logger.getLogger("JWTAuthFilter log");
    private static final String API_DATA_SOURCE_PATH = "/api-datasource/**";
    private String[] skipAuthUrls;
    @Autowired
    AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();
        logger.info(reqPath);
        for(int i = 0;i < skipAuthUrls.length;i++){
            logger.info(skipAuthUrls[i]);
        }
        //跳过不需要验证的路径
        if(null != skipAuthUrls&& Arrays.asList(skipAuthUrls).contains(reqPath)){
            logger.info("pass!!!");
            return chain.filter(exchange);
        }

        /*******datasource*******/

        AntPathMatcher dataSourceMatcher = new AntPathMatcher();
        if(dataSourceMatcher.match(API_DATA_SOURCE_PATH, reqPath)){
            logger.info("datasource 发来的");
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if(StringUtil.isNullOrEmpty(token)){
                throw new RuntimeException("无token");
            }
            logger.info("token = " + token);
            String  dataSourceId = authService.verifyDataSource(token);
            logger.info("dataSourceId = " + dataSourceId);
            if (dataSourceId == null) {
                logger.warning("token 无效" + reqPath);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(SystemConstant.HEADER_KEY_OF_DATA_SOURCE, dataSourceId ).build();
            return chain.filter(exchange.mutate().request(request).build());
        }

        /******datasource********/


        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern : skipAuthUrls) {
            boolean match = false;
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, reqPath);
            } else {
                match = reqPath.equals(pattern);
            }
            if (match) {
                logger.info("pass!!!");
                return chain.filter(exchange);
            }
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");
        if(StringUtil.isNullOrEmpty(token)){
            throw new RuntimeException("无token");
        }
        logger.info("token = " + token);
        User user = authService.verifyToken(reqPath, token);
        if (user == null) {
            logger.warning("没有授权的访问" + reqPath);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }


        logger.info("存入header的user " + user.toString());
        logger.info(JSONObject.toJSONString(user));
        //获取token中存储的用户唯一标识userPhone，并放入request header中，供后端业务服务使用

        //需要编码再解码
        String encode = null;
        try {
            encode = URLEncoder.encode(JSONObject.toJSONString(user), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(SystemConstant.HEADER_KEY_OF_USER, encode ).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 过滤器的优先级，越低越高
     */
    @Override
    public int getOrder() {
        return -1;
    }
}