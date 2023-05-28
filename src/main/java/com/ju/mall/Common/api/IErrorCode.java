package com.ju.mall.Common.api;

/**
 * @author: jcm
 * @description: TODO 返回失败的封装
 * @date: 2023/5/28 16:40
 * @version: 1.0
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
