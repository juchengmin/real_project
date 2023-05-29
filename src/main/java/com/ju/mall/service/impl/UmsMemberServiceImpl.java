package com.ju.mall.service.impl;

import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.service.RedisService;
import com.ju.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @projectName: mall
 * @package: com.ju.mall.service.impl
 * @className: UmsMemberServiceImpl
 * @author: Eric
 * @description: TODO
 * @date: 2023/5/29 10:35
 * @version: 1.0
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private RedisService redisService;
    /**
     * TODO @Value将外部配置文件中的值注入到Spring组件中
     */
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTHCODE;

    @Value("${redis.key.expire.authCode}")
    private Long REDIS_KEY_EXPIRE_AUTHCODE;

    @Override
    public CommonResult generatorAuthCode(String telephone) {
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            //random.nextInt(10) 生成0-9之间的随机数
            authCode.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(this.getRealKey(telephone),authCode.toString());
        redisService.expire(this.getRealKey(telephone),REDIS_KEY_EXPIRE_AUTHCODE);
        return CommonResult.success(authCode.toString(),"获取验证码成功");
    }

    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if(StringUtils.isEmpty(authCode)){
            return CommonResult.validateFailed("请输入验证码");
        }
        String realAuthCode = redisService.get(this.getRealKey(telephone));
        boolean result = authCode.equals(realAuthCode);
        if(result){
            return CommonResult.success(null,"验证码检验成功");
        }else{
            return CommonResult.validateFailed("验证码不正确");
        }
    }

    private String getRealKey(String telephone){
        return REDIS_KEY_PREFIX_AUTHCODE+telephone;
    }
}
