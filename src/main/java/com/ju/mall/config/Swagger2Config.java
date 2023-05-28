package com.ju.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @projectName: mall
 * @package: com.ju.mall.config
 * @className: Swagger2Config
 * @author: Eric
 * @description: TODO @EnableSwagger2 用于在 Spring Boot 应用程序中启用 Swagger 2 文档生成工具
 * TODO @Configuration 交给spring管理的同时,表示了是配置类,和@AliasFor注解可以用于两个属性之间的相互映射，使得它们可以互为别名使用。当使用其中一个属性时，另一个属性的值也会被自动赋值
 * @date: 2023/5/28 18:46
 * @version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
    * @param :
    * @return Docket
    * @author jcm
    * @description TODO Docket 对象是 Swagger 框架中的一个核心类，用于配置和构建 Swagger API 文档的生成规则和属性。
     * TODO 通过创建和配置 Docket 对象，我们可以定义要生成的 API 文档的各种属性，包括文档的标题、描述、版本号、接口路径、接口过滤规则等。
    * @date 2023/5/28 19:02
    */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前controller的包生成api文档
                .apis(RequestHandlerSelectors.basePackage("com.ju.mall.controller"))
                //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("mall")
                .contact("macro")
                .version("1.0.0")
                .build();
    }
}
