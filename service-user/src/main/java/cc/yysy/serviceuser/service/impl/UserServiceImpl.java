package cc.yysy.serviceuser.service.impl;

import cc.yysy.serviceuser.mapper.UserMapper;
import cc.yysy.serviceuser.service.UserService;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
import cc.yysy.utilscommon.exception.BizException;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.result.ResultCode;
import cc.yysy.utilscommon.utils.HASHUtils;
import cc.yysy.utilscommon.utils.IOUtils;
import cc.yysy.utilscommon.utils.JWTUtils;
import cc.yysy.utilscommon.utils.RsaUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    static Logger logger = Logger.getLogger("UserServiceImpl log");

    @Resource
    UserMapper userMapper;

    public static UserServiceImpl userService;

    //必须写上的，不能删
    @PostConstruct
    public void init(){
        userService=this;
    }

    public User getUser(String userPhone){
        User sysUser = userMapper.selectByPhoneNumber(userPhone);
        logger.info("取出user");
        logger.info(sysUser.toString());
        return sysUser;
    }

    /**
     * 登录和注册前都需要先获取RSA公钥
     * @param loginName
     * @return
     */
    @Override
    public String getPubKey(String loginName) {
        String pubKeyStr = null;
        String userPhone = getPhoneNumber(loginName);
        logger.info("获取到的手机号："+userPhone);
//        if(userPhone != null){
//            throw new BizException("已经存在用户名 | 手机号 | 邮箱");
//        }

        if(userPhone == null) userPhone = loginName;
        try {
            //找一下这个手机号之前有没有存过
            File file=new File("keys/pubKeys/"+userPhone+".dll");
            logger.info("===================");
            logger.info("开始尝试获取密钥");
            if(!file.exists())
            {
                logger.info("不存在，生成密钥");
                //如果不存在就生成
                RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
//                System.out.println(keyPair);

                // 获取 公钥 和 私钥
                String pubKey = keyPair.getPublicKey();
                String priKey = keyPair.getPrivateKey();

                logger.info("用户 "+userPhone +"得到密钥：");

                logger.info("公钥 + "+pubKey);
                logger.info("私钥 + "+priKey);
                // 保存 公钥 和 私钥
                IOUtils.writeFile(pubKey, new File("keys/pubKeys/"+userPhone+".dll"));
                IOUtils.writeFile(priKey, new File("keys/priKeys/"+userPhone+".dll"));

                logger.info("公钥 + "+IOUtils.readFile(new File("keys/pubKeys/"+userPhone+".dll")));
                logger.info("私钥 + "+IOUtils.readFile(new File("keys/priKeys/"+userPhone+".dll")));
            }

            //因为需要BASE64格式的公钥，所以直接读文件就可以了。
            pubKeyStr = IOUtils.readFile(new File("keys/pubKeys/"+userPhone+".dll"));
            logger.info("得到公钥" + pubKeyStr);
            return pubKeyStr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private String decryptPassword(String phoneNumber, String password){
        String priKey = null;
        try {
            priKey = IOUtils.readFile(new File("keys/priKeys/" + phoneNumber + ".dll"));

            logger.info("当前用户发送的密码是 " + password);
            logger.info("当前的私钥是 " + priKey);

            //RSA解密
            String newPassword = RsaUtils.decryptByPrivateKey(priKey, password);
            logger.info("解密后的密码是 " + newPassword);
            return newPassword;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void reSetPassword(User user) {
        logger.info("注册开始 当前用户的手机号是" + user.getPhoneNumber());
        String priKey = null;
        try {
            //RSA解密
            String newPassword = decryptPassword(user.getPhoneNumber(), user.getPassword());
            logger.info("解密后的密码是 " + newPassword);

            //加盐哈希
            String finalPassword = HASHUtils.saltEncode(newPassword);
            user.setPassword(finalPassword);

            logger.info("加盐hash后的密码是 " + finalPassword);
            //存入用户信息到数据库中
            logger.info("存入用户信息");
        }catch (Exception e) {
                e.printStackTrace();
        }
    }
    /**
     * 注册
     * @param sysUser
     * @return
     */
    @Override
    public boolean signup(User sysUser) {
        // 读取私钥文件, 创建私钥对象
        try {
            //重置密码
            reSetPassword(sysUser);
            logger.info(JSON.toJSONString(sysUser));
            int res = userMapper.insertUser(sysUser);
            logger.info("存入结果 " + res);
            return res == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户的Phone
     * @param loginName
     * @return
     */
    private String getPhoneNumber(String loginName){
        return userMapper.selectPhoneNumber(loginName);
    }


    @Override
    public Result login(String loginName, String password) {
        String priKey = null;
        //获取用户的手机号
        String userPhone = getPhoneNumber(loginName);
        logger.info("获取到的手机号为 "+userPhone);
        if(userPhone == null){
            return Result.error(ResultCode.USER_LOGIN_ERROR);
//            throw new BizException("不存在该用户名 | 手机号 | 邮箱");
        }
        try {
//            logger.info("用户手机号为 "+userPhone);
//            logger.info("用户密码为 "+password);
//            priKey = (IOUtils.readFile(new File("keys/priKeys/"+userPhone+".dll")));
//            // 用私钥解密数据
//            logger.info("取出私钥");
//            logger.info(priKey);
//            byte[] plainData = RSAUtils.decrypt(password, priKey);
//            String newPassword = new String(plainData);

            String newPassword = decryptPassword(userPhone,password);
            System.out.println("解密后的密码：" + newPassword);
            //从数据库取出密码
            String userPassword = userMapper.selectPassword(userPhone);
            System.out.println("数据库取出的密码为：" + userPassword);
//            //匹配
            boolean match = HASHUtils.saltMatches(newPassword,userPassword);
            if(!match) {
                System.out.println("不匹配，登录失败！");
                return Result.error(ResultCode.USER_LOGIN_ERROR);
            }
//
//           //登录成功
//           System.out.println("登录成功！！！");

            //取出用户
            User user = getUser(userPhone);

            String token = JWTUtils.getToken(user);

            return Result.success(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(ResultCode.SYSTEM_ERROR);
    }

    @Override
    public void logout(String token) {

    }

    public String getToken(User user){
        return JWTUtils.getToken(user);
    }

//    public String getCoupleToken(User user){
//        Map<String, String> coupleToken = Maps.newHashMap();
//        coupleToken.put("wds134", "hj157");
//        coupleToken.put("hj157", "wds134");
//        if(coupleToken.containsKey(user.getPhoneNumber())){
//            String couplePhone = coupleToken.get(user.getPhoneNumber());
//            User coupleUser = getUser(couplePhone);
//            String token = JWTUtils.getToken(coupleUser);
//            return token;
//        }
//        return null;
//    }

    /**************************** admin ****************************/

    /**
     * 修改用户信息
     * @param newUser
     * @return
     */
    public Result updateUser(User newUser){
        if(newUser.getPassword() != null)
            reSetPassword(newUser);
        logger.info(newUser.toString());
        return Result.resultDB(userMapper.updateByPhoneNumber(newUser));
    }


    /**
     * 获取用户列表
     * @return
     */
    public Result getUserList(){
        return Result.success(userMapper.selectList());
    }

    public Result getUserListByQuery(Map<String,Object> params){
        int total = userMapper.countUserListByQuery(params);
        List<User> list = userMapper.getUserListByQuery(params);
        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",list);
        return Result.success(result);
    }

    /*************************** user ***************************/
    public Result updateUserPassword(User user, String oldPassword, String newPassword) {
        logger.info(user.getPhoneNumber());
        oldPassword = decryptPassword(user.getPhoneNumber(), oldPassword);
        //从数据库取出密码
        String userPassword = userMapper.selectPassword(user.getPhoneNumber());
        logger.info("数据库取出的密码为：" + userPassword);
        logger.info("旧密码为 "+oldPassword);
//        System.out.println("数据库取出的密码为：" + userPassword);
//      //匹配
        boolean match = HASHUtils.saltMatches(oldPassword,userPassword);
        if(!match) {
            logger.info("不匹配，旧密码输入错误！");
//            System.out.println("不匹配，旧密码输入错误！");
            return Result.error(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        user.setPassword(newPassword);
        reSetPassword(user);
        return Result.resultDB(userMapper.updateByPhoneNumber(user));
    }

    public Result modifyUserType(Integer userId, String userType) {
        return Result.resultDB(userMapper.updateUserType(userId,userType));
    }

    public Result addUser(User newUser) {
        // 读取私钥文件, 创建私钥对象
        try {
            //重置密码
            reSetPassword(newUser);
            logger.info(JSON.toJSONString(newUser));
            return Result.resultDB(userMapper.insertUser(newUser));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(ResultCode.SYSTEM_ERROR);
    }

    public Result deleteUser(Integer userId) {
        return Result.resultDB(userMapper.deleteUser(userId));
    }
}
