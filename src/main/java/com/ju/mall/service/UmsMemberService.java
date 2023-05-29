package com.ju.mall.service;

import com.ju.mall.Common.api.CommonResult;

/**
 * @author: jcm
 * @description: TODO 用户验证码接口
 * @date: 2023/5/29 10:32
 * @version: 1.0
 */
public interface UmsMemberService {
    /**
     * @param telephone:
     * @return CommonResult
     * @author jcm
     * @description TODO 生成验证码
     * @date 2023/5/29 10:33
     */
    CommonResult generatorAuthCode(String telephone);

    /**
     * @param telephone:
     * @param authCode:
     * @return CommonResult
     * @author jcm
     * @description TODO 校验验证码
     * @date 2023/5/29 10:34
     */
    CommonResult verifyAuthCode(String telephone,String authCode);
}
