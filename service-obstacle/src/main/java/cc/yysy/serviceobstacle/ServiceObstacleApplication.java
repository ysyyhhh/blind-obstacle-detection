package cc.yysy.serviceobstacle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.yysy.serviceobstacle.mapper")
@EnableScheduling
public class ServiceObstacleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceObstacleApplication.class, args);
    }

}