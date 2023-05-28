package com.ju.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: jcm
 * @description: TODO Springboot项目启动类
 * TODO @SpringBootApplication 注解：自动扫描组件、加载配置 ，包含了以下三个注解的功能
 * TODO 1.@Configuration：表示该类是一个配置类，用于定义和配置Bean。
 * TODO 2.@EnableAutoConfiguration：启用Spring Boot的自动配置机制，根据类路径下的依赖自动配置应用程序。
 * TODO 3.@ComponentScan：扫描并加载Spring组件，包括控制器、服务、存储库等。
 * @date: 2023/5/28 10:46
 * @version: 1.0
 */
@SpringBootApplication
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
