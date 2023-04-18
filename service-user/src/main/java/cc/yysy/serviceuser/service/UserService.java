package cc.yysy.serviceuser.service;

import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.result.Result;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 注册1 密钥交付
     * @param userPhone
     * @return 公钥
     */
    public String getPubKey(String userPhone);

    /**
     * 注册2 正式注册
     * @param sysUser
     * @return
     */
    public boolean signup(User sysUser);

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    public Result login(String loginName, String password);



    /**
     * 登出
     * @param token
     */
    public void logout(String token);


}
