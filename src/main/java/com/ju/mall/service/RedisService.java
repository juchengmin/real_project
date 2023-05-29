package com.ju.mall.service;

/**
 * @author: jcm
 * @description: TODO redis服务接口
 * @date: 2023/5/29 10:19
 * @version: 1.0
 */
public interface RedisService {

    /**
     * @param key:
     * @param value:
     * @return void
     * @author jcm
     * @description TODO 存放数据
     * @date 2023/5/29 10:20
     */
    void set(String key,String value);

    /**
     * @param key:
     * @return String
     * @author jcm
     * @description TODO 获取数据
     * @date 2023/5/29 10:20
     */
    String get(String key);

    /**
     * @param key:
     * @param expire:
     * @return boolean
     * @author jcm
     * @description TODO 设置过期时间
     * @date 2023/5/29 10:21
     */
    boolean expire(String key,long expire);

    /**
     * @param key:
     * @return void
     * @author jcm
     * @description TODO 删除数据
     * @date 2023/5/29 10:22
     */
    void remove(String key);

    /**
     * @param key:
     * @param delta: 自增长度
     * @return Long
     * @author jcm
     * @description TODO 自增
     * @date 2023/5/29 10:23
     */
    Long increment(String key,long delta);
}
