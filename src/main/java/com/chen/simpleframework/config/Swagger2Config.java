package com.chen.simpleframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: cz
 * Date： 2020/12/13 18:57
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * Swagger信息
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //api基本信息
                .apiInfo(apiInfo())
                //设置允许暴露的接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chen.simpleframework.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api基本信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //项目名字
                .title("simpleframework项目")
                //项目描述
                .description("集成一部分功能通用框架")
                //联系人(人名，项目地址，邮箱)
                .contact(new Contact("猪八戒", "", ""))
                //版本号
                .version("1.0.0")
                .build();
    }
}
