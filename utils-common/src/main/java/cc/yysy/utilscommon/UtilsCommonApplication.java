package cc.yysy.utilscommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UtilsCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilsCommonApplication.class, args);
    }

}
