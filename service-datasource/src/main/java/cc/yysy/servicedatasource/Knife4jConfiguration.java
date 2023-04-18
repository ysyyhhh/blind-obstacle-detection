package cc.yysy.servicedatasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * description: knife4j配置文件
 *
 * @author: weirx
 * @time: 2022/1/18 9:44
 */
@Data
@Configuration
@EnableSwagger2WebMvc
@ConfigurationProperties(prefix="swagger")
public class Knife4jConfiguration {


    private String version;

    private String termsOfServiceUrl;

    private String description;

    private String basePackage;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description(description)
                        .termsOfServiceUrl(termsOfServiceUrl)
                        .version(version)
                        .build())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}