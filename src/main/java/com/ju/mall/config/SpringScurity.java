package com.ju.mall.config;

import com.ju.mall.component.JwtAuthenticationTokenFilter;
import com.ju.mall.component.RestAuthenticationEntryPoint;
import com.ju.mall.component.RestfulAccessDeniedHandler;
import com.ju.mall.dto.AdminUserDetails;
import com.ju.mall.mbg.model.UmsAdmin;
import com.ju.mall.mbg.model.UmsPermission;
import com.ju.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.config
 * @className: SpringScurity
 * @author: Eric
 * @description: TODO SpringScurity的配置类 @EnableGlobalMethodSecurity(prePostEnabled = true) 是一个 Spring Security 注解，用于启用全局方法级别的安全性配置
 * TODO WebSecurityConfigurerAdapter 是一个用于定制和配置 Spring Security 的类，它提供了一种方便的方式来自定义应用程序的安全性配置
 * @configure类中对自己类中@bean的引用，用方法产生即可，因为在同一个类中Spring 容器还没有完成 @bean方法的执行，所以无法通过自动装配来获取该实例
 * @Qualifier("myBean") 根据名称注入
 * @Autowired 根据类型注入
 * @date: 2023/5/29 16:35
 * @version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringScurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    /**
     * @param httpSecurity:
     * @return void
     * @author jcm
     * @description TODO 重写springsecurity的策略
     * TODO configure(HttpSecurity httpSecurity) 是 Spring Security 中的一个方法，用于配置 Web 安全性。
     * TODO configure(HttpSecurity httpSecurity) 方法是在 WebSecurityConfigurerAdapter 类中重写的方法，用于定义 Spring Security 的安全规则和配置。
     * TODO 通过重写该方法，您可以定义诸如请求授权、身份验证、跨站点请求伪造（CSRF）保护、会话管理等方面的安全规则
     * @date 2023/5/29 16:46
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //CSRF 是一种攻击方式，攻击者利用用户的身份在用户不知情的情况下执行恶意请求。为了防止 CSRF 攻击，Spring Security 提供了默认的 CSRF 保护机制，其中包括生成和验证 CSRF 令牌。
        //HttpSecurity.csrf() 方法允许您在 Spring Security 中配置 CSRF 保护的行为
        httpSecurity.csrf()
                //禁用 CSRF
                .disable()
                //定义和配置会话管理
                .sessionManagement()
                //配置会话管理的策略 (SessionCreationPolicy.STATELESS) 是 Spring Security 中用于配置会话创建策略为无状态（stateless）的方法
                //在无状态的会话创建策略下，Spring Security 不会创建任何会话来存储用户的状态信息。这意味着每个请求都必须携带身份验证的凭据（如令牌或用户名/密码），并且每次请求都需要重新验证身份。
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //定义哪些请求需要经过身份验证和授权才能访问。您可以使用链式调用方法来定义不同请求路径的访问规则。
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                ).permitAll()
                //用户登录和注册放开
                .antMatchers("/admin/login", "/admin/register")
                .permitAll()
                .antMatchers("/esProduct/**")// 搜索模块暂时运行匿名访问
                .permitAll()
                .antMatchers("/esProduct/**","/member/readHistory/**")// 搜索及会员浏览记录暂时允许匿名访问
                .permitAll()
                //跨域请求会先经过一次OPTIONS请求
                //OPTIONS 请求通常用于跨域资源共享（CORS）中的预检请求。在进行跨域请求时，浏览器会首先发送一个 OPTIONS 请求，以确定实际请求是否安全和允许访问。
                //服务器在响应 OPTIONS 请求时，会返回包含允许的请求方法、请求标头和其他支持的选项的响应头。
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();
            //禁用缓存
            httpSecurity.headers().cacheControl();
            //添加JWT过滤器
            httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            //添加自定义授权和未登录结果返回
            httpSecurity.exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .accessDeniedHandler(restfulAccessDeniedHandler);
    }

    /**
     * @param auth:
     * @return void
     * @author jcm
     * @description TODO 用于配置身份验证（Authentication）的方式
     * TODO 通过重写 configure(AuthenticationManagerBuilder auth) 方法，您可以定义用于验证用户身份的认证机制和用户详细信息的来源。
     * @date 2023/5/29 17:02
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * @param :
     * @return PasswordEncoder
     * @author jcm
     * @description TODO 创建加密密码的bean
     * @date 2023/5/29 22:40
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Spring Security 提供的一个密码加密器（PasswordEncoder）实现，用于安全地对密码进行哈希加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            UmsAdmin admin = adminService.getAdminByUsername(username);
            if(admin != null){
                //获取权限
                List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                return new AdminUserDetails(admin,permissionList);
            }
            throw new UsernameNotFoundException("用户名或者密码错误");
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
