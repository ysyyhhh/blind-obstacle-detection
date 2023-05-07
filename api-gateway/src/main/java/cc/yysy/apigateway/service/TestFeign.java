package cc.yysy.apigateway.service;

import cc.yysy.utilscommon.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class TestFeign {

    @Autowired
    private UserService userService;
    @Test
    public void test() {
        User user =  userService.getUser("12345678901");
        if(user != null){
            System.out.println("user is not null");

            System.out.println(user);
        }
//        System.out.println("test");
    }
}
