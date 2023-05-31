package com.ju.mall.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @projectName: mall
 * @package: com.ju.mall.dto
 * @className: UserAdminLoginParam
 * @author: Eric
 * @description: TODO 接收用户信息,并且添加了注解，对接收时的参数进行判断
 * @date: 2023/5/31 11:35
 * @version: 1.0
 */
public class UmsAdminLoginParam {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

    public UmsAdminLoginParam() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
