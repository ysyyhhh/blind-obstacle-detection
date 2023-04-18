package cc.yysy.servicedatasource.controller;

import cc.yysy.utilscommon.utils.IOUtils;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestController {

    @Test
    public void pathTest(){
// 通过 System.getProperty("user.dir") 方式获取到项目根目录
        String projectRootDirectoryPath = System.getProperty("user.dir");
        System.out.println("当前项目根目录为:\t" + projectRootDirectoryPath);
// 通过 File 对象的 getParent() 方法获取到根目录的上级目录
        String parentPath = new File(projectRootDirectoryPath).getParent();
        System.out.println("当前项目根目录的上级目录为:\t" + parentPath);
    }

    @Test
    public void getFiles(){
        List<String> images = IOUtils.findDir("D:\\program_date_store\\my_code\\blind_obstacle_detection\\images\\raw\\1");
        for(String image:images){
            System.out.println(image);
        }
    }
}
