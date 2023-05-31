package com.ju.mall.controller;

import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.dto.UmsAdminLoginParam;
import com.ju.mall.mbg.model.UmsAdmin;
import com.ju.mall.mbg.model.UmsPermission;
import com.ju.mall.service.UmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: mall
 * @package: com.ju.mall.controller
 * @className: UmsController
 * @author: Eric
 * @description: TODO 用户登录注册管理
 * @date: 2023/5/31 15:18
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class UmsController {

    @Autowired
    private UmsAdminService umsAdminService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam){
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if(umsAdmin == null){
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "用户登录,登录成功以后返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminParam){
        String token = umsAdminService.login(umsAdminParam.getUsername(), umsAdminParam.getPassword());
        if(StringUtils.isEmpty(umsAdminParam)){
            return CommonResult.validateFailed("用户名或者密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取用户所有的权限")
    @RequestMapping(value = "/permission/{adminId}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsPermission>> register(@PathVariable Long adminId){
        return CommonResult.success(umsAdminService.getPermissionList(adminId));
    }
}
