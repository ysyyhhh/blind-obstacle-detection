package cc.yysy.servicearea;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.yysy.servicearea.mapper")
@EnableScheduling
public class ServiceAreaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAreaApplication.class, args);
    }

}