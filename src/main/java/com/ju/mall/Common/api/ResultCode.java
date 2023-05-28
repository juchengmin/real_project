package com.ju.mall.Common.api;

/**
 * @projectName: mall
 * @package: com.ju.mall.Common.api
 * @className: ResultCode
 * @author: Eric
 * @description: TODO 返回值的控制
 * @date: 2023/5/28 16:30
 * @version: 1.0
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(404,"参数校验失败"),
    UNAUTHORIZED(401,"暂未登录或token已经过期"),
    FORBIDDEN(403,"没有相关的权限"),
    ;
    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
