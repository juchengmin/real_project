package com.ju.mall.service;

import com.ju.mall.mbg.model.UmsAdmin;
import com.ju.mall.mbg.model.UmsPermission;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.service
 * @className: UmsAdminService
 * @author: Eric
 * @description: TODO 用户操作接口
 * @date: 2023/5/29 17:07
 * @version: 1.0
 */
public interface UmsAdminService {

    /**
     * @param username:
     * @return UmsAdmin
     * @author jcm
     * @description TODO 通过用户名获取后台管理员
     * @date 2023/5/29 17:20
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * @param umsAdminParam:
     * @return UmsAdmin
     * @author jcm
     * @description TODO 用户注册功能
     * @date 2023/5/29 17:21
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * @param username:
     * @param password:
     * @return String
     * @author jcm
     * @description TODO 用户登录功能
     * @date 2023/5/29 17:22
     */
    String login(String username,String password);

    /**
     * @param userId:
     * @return List<UmsPermission>
     * @author jcm
     * @description TODO 获取用户的所有权限
     * @date 2023/5/29 17:25
     */
    List<UmsPermission> getPermissionList(Long userId);
}
