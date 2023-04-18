package cc.yysy.utilscommon.service.fallback;



import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.service.UserService;
import org.springframework.stereotype.Component;

//容错类，实现feign所在的接口，并且去实现fegin的所有方法
//一旦feign远程调用出错，就进入当前类的同名方法
@Component
public class UserServiceFallback implements UserService {
    @Override
    public User getUser(String userPhone) {
        User user = new User();
        return null;
    }
}
