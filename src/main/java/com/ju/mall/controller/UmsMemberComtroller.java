package com.ju.mall.controller;

import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @projectName: mall
 * @package: com.ju.mall.controller
 * @className: UmsMemberComtroller
 * @author: Eric
 * @description: TODO 用户验证码校验
 * @date: 2023/5/29 10:50
 * @version: 1.0
 */
@Api(tags = "UmsMemberComtroller",description = "会员登录注册管理")
@Controller
@RequestMapping("/sso")
public class UmsMemberComtroller {

    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult generatorAuthCode(String telephone){
        return umsMemberService.generatorAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @RequestMapping(value = "/verifyAuthCode",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult verifyAuthCode(String telephone,String authCode){
        return umsMemberService.verifyAuthCode(telephone,authCode);
    }

}
