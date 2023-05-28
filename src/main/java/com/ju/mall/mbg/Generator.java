package com.ju.mall.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.mbg
 * @className: Generator
 * @author: Eric
 * @description: TODO 执行逆向工程的类
 * @date: 2023/5/28 11:08
 * @version: 1.0
 */
public class Generator {
    public static void main(String[] args) throws Exception{
        //读取我们自己的mbg配置文件,涉及资源读取的操作,采用try-with-resources,确保资源在使用完毕后得到正确关闭，避免资源泄漏和手动关闭的繁琐操作。
        try(InputStream myGeneratorConfig = Generator.class.getResourceAsStream("/generatorConfig.xml")) {
            //存放警告信息
            List<String> warnings = new ArrayList<>();
            //生成的信息重复时,覆盖原代码
            boolean overwrite = true;
            //生成配置文件解析类
            ConfigurationParser warningsConfigurationParser = new ConfigurationParser(warnings);
            //将读取到的xml配置文件解析为执行时所需的配置类对象
            Configuration config = warningsConfigurationParser.parseConfiguration(myGeneratorConfig);

            DefaultShellCallback defaultShellCallback = new DefaultShellCallback(overwrite);

            //创建mbg对象
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, defaultShellCallback, warnings);
            //执行文件的生成
            myBatisGenerator.generate(null);
            //输出警告信息
            warnings.forEach(
                    warning -> System.out.println(warning)
            );
        }
    }
}
