package com.ju.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ju.mall.Common.utils.JwtTokenUtil;
import com.ju.mall.dao.UmsAdminRoleRelationDao;
import com.ju.mall.mbg.mapper.UmsAdminMapper;
import com.ju.mall.mbg.model.UmsAdmin;
import com.ju.mall.mbg.model.UmsAdminExample;
import com.ju.mall.mbg.model.UmsPermission;
import com.ju.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.service.impl
 * @className: UmsAdminService
 * @author: Eric
 * @description: TODO
 * @date: 2023/5/29 17:26
 * @version: 1.0
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;
    /**
     * @Lazy 是一个 Spring 框架中的注解，用于延迟加载（Lazy Loading）Bean 的初始化过程。当一个 Bean 被标记为 @Lazy 时，它将在首次使用时才会被实例化和初始化，而不是在容器启动时就立即创建。
     * 如果我们没有配置自己的userDetailsService,就会使用容器的
     */
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        //构建查询条件
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        if(CollectionUtil.isNotEmpty(umsAdmins)){
            return umsAdmins.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        //有重名的用户则不给注册
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(umsAdminParam.getUsername());
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        if(CollectionUtil.isNotEmpty(umsAdmins)){
            return null;
        }
        //需要自己补充参数信息
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //密码进行加密
        umsAdmin.setPassword(passwordEncoder.encode(umsAdminParam.getPassword()));
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try{
            //是Spring Security 中的一个方法调用，用于根据给定的用户名加载用户的详细信息。
            //请确保在使用 loadUserByUsername(username) 方法之前，已经正确配置了用户详细信息服务（UserDetailsService）和相应的用户存储（例如数据库、LDAP 等）。
            //这样才能正确地加载用户详细信息并进行身份验证。
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            //表示基于用户名和密码的身份验证令牌,第三个参数是用户的权限信息
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //用于将给定的身份验证对象（Authentication）设置为当前线程的安全上下文中的身份验证对象
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generatorToken(userDetails);
        }catch (Exception e){
            LOGGER.warn("登录异常:{}",e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long userId) {
        return umsAdminRoleRelationDao.getPermissonList(userId);
    }
}
