package cc.yysy.servicedatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cc.yysy.servicedatasource.mapper")
@EnableScheduling
public class ServiceDataSourceApplication {
        public static void main(String[] args) {
            SpringApplication.run(ServiceDataSourceApplication.class, args);
        }
}
