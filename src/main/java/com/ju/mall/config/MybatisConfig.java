package com.ju.mall.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: mall
 * @package: com.ju.mall.config
 * @className: MybatisConfig
 * @author: Eric
 * @description: TODO Mybatis的配置类:@Configuration 表明是一个配置类,@MapperScan 扫描哪些包
 * @date: 2023/5/28 10:53
 * @version: 1.0
 */

@Configuration
@MapperScan("com.ju.mall.mbg.mapper")
public class MybatisConfig {
}
