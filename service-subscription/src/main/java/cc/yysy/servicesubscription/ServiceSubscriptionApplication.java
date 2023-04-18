package cc.yysy.servicesubscription;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.yysy.servicesubscription.mapper")
@EnableScheduling
public class ServiceSubscriptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSubscriptionApplication.class, args);
    }
}
