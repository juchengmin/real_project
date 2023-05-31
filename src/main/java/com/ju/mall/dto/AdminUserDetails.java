package com.ju.mall.dto;

import com.ju.mall.mbg.model.UmsAdmin;
import com.ju.mall.mbg.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: mall
 * @package: com.ju.mall.dto
 * @className: AdminUserDetails
 * @author: Eric
 * @description: TODO 存放用户信息,包括权限,自己系统的用户信息转换为springsecurity所需的用户信息
 * @date: 2023/5/30 9:48
 * @version: 1.0
 */
public class AdminUserDetails implements UserDetails {
    /**
     * 用户信息
     */
    private UmsAdmin umsAdmin;

    /**
     * 权限信息,一系列权限,表明用户所需的一系列的权限
     */
    private List<UmsPermission> umsPermission;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> umsPermission) {
        this.umsAdmin = umsAdmin;
        this.umsPermission = umsPermission;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限 .map()转换为新的流
        return umsPermission.stream()
                .filter(umsPermission -> !StringUtils.isEmpty(umsPermission.getValue()))
                .map(umsPermission -> new SimpleGrantedAuthority(umsPermission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
