package com.ju.mall.dao;

import com.ju.mall.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.dao
 * @className: UmsAdminRoleRelationDao
 * @author: Eric
 * @description: TODO 自定义的dao层,查询关联关系
 * @date: 2023/5/29 17:45
 * @version: 1.0
 */
@Mapper
public interface UmsAdminRoleRelationDao {
    /**
     * @param userId:
     * @return List<UmsPermission>
     * @author jcm
     * @description TODO 根据用户id获取用户的参数  @Param 方法参数在 SQL 语句中的对应关系
     * @date 2023/5/29 17:47
     */
    List<UmsPermission> getPermissonList(@Param("adminId") Long userId);
}
