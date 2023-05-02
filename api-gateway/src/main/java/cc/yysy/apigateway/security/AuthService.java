package cc.yysy.apigateway.security;


import cc.yysy.apigateway.service.UserService;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.exception.BizException;
import cc.yysy.utilscommon.utils.JWTUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.logging.Logger;

@Service
public class AuthService {
    static Logger logger = Logger.getLogger("AuthService log");

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    UserService userService;
    /**
     * token获取用户对象
     * @param token
     * @return
     */
    public User verify(String token){
        logger.info(token);
        // 获取 token 中的 userPhone
        String userPhone;
        try {
            userPhone = JWT.decode(token).getAudience().get(0);
            Date pre = JWT.decode(token).getExpiresAt();
            Date now = new Date();
            if(now.after(pre)){
                throw new RuntimeException("过期了！！！");
//                return false;
            }
        } catch (JWTDecodeException j) {
            throw new RuntimeException("token 错误 401");
        }
        //获取user对象
        User user = userService.getUser(userPhone);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }

        logger.info(user.toString());
        //校验token与user对象是否正确
        if(JWTUtils.verify(token,user)){
            return user;
        }

        return null;
    }

    public String verifyDataSource(String token) {
//        logger.info(token);
        // 获取 token 中的 userPhone
        String dataSourceId = null;
        dataSourceId = JWT.decode(token).getAudience().get(0);
        return dataSourceId;
    }
    public User verifyToken(String reqPath, String token) {
        //通过token获取用户
        User user = verify(token);

        //判断权限
        if(verifyPathAuth(reqPath,user)){
            return user;
        }

        throw new BizException("无权限！");
    }

    private boolean verifyPathAuth(String reqPath, User user) {
        logger.info(reqPath);
        String urlPermission = getUrlPermission(reqPath);
        logger.info(urlPermission);
        // 如果url仅要求验证用户有效性，则直接通过
        logger.info(String.valueOf(StringUtil.isNullOrEmpty(urlPermission)));
//        logger.info(String.valueOf(urlPermission.compareTo("authc")));
        if (!StringUtil.isNullOrEmpty(urlPermission) && (urlPermission.compareTo("authc") == 0) ){
            return true;
        }
        logger.info("进一步判断用户权限");
        // 进一步判断用户权限
        if (urlPermission.startsWith("perms")) {
            Set<String> userPerms = this.getUserPermissions(user);
            String perms = urlPermission.substring(urlPermission.indexOf("[") + 1, urlPermission.lastIndexOf("]"));
            logger.info("account:"+user.toString() +  userPerms +  perms);
            return userPerms.containsAll(Arrays.asList(perms.split(",")));
        }
        return false;
    }

    /**
     * 获取所有的接口url与用户权限的映射关系,格式仿造了shiro的权限配置格式
     */
    public Map<String, String> getAllUrlPermissionsMap() {
        Map<String, String> urlPermissionsMap = Maps.newHashMap();
        urlPermissionsMap.put("/service-user/api/getUser", "authc");
        urlPermissionsMap.put("/service-user/api/test", "authc");
        urlPermissionsMap.put("/service-user/api/updateToken", "authc");
        urlPermissionsMap.put("/service-timetable/timetable/**", "authc");
        urlPermissionsMap.put("/service-timetable/task/**", "authc");
        urlPermissionsMap.put("/service-timetable/classList/**", "authc");
        urlPermissionsMap.put("/service-user/user/**", "authc");
        urlPermissionsMap.put("/service-obstacle/user/**", "authc");
        urlPermissionsMap.put("/service-subscription/user/**", "authc");
        urlPermissionsMap.put("/**", "perms[admin]");

//        urlPermissionsMap.put("/user-service/signup", "authc");
//        urlPermissionsMap.put("/order-service/", "perms[order]");
//        urlPermissionsMap.put("/storage-service/api/storage/**", "perms[storage]");
        return urlPermissionsMap;
    }

    public static final String ADMIN_TYPE = "admin";
    public static final String USER_TYPE = "user";
    /**
     * 根据用户的唯一标识获取该用户的权限列表
     */
    public Set<String> getUserPermissions(User user) {
        if (user.getUserType().matches(ADMIN_TYPE) ) {
            return Sets.newHashSet("admin", "user");
        }else if(user.getUserType().matches(USER_TYPE) ){
            return Sets.newHashSet("user");
        }
        return Collections.emptySet();
    }

    /**
     * 根据一个确定url获取该url对应的权限设置
     * 利用AntPathMatcher进行模式匹配
     */
    private String getUrlPermission(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }
        Map<String, String> urlPermissionsMap = getAllUrlPermissionsMap();
        logger.info(urlPermissionsMap.toString());
        Set<String> urlPatterns = urlPermissionsMap.keySet();
        for (String pattern : urlPatterns) {
            boolean match = false;
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, url);
            } else {
                match = url.equals(pattern);
            }
            if (match) {
                return urlPermissionsMap.get(pattern);
            }
        }
        return null;
    }


}