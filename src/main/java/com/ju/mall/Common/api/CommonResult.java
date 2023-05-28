package com.ju.mall.Common.api;

/**
 * @projectName: mall
 * @package: com.ju.mall.Common.api
 * @className: CommonResult
 * @author: Eric
 * @description: TODO  用对返回对象的封装
 * @date: 2023/5/28 16:33
 * @version: 1.0
 */
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    /**
     * @param :
     * @return null
     * @author jcm
     * @description TODO 默认的构造方法
     * @date 2023/5/28 16:34
     */
    public CommonResult() {
    }

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @param data:
     * @return CommonResult<T>
     * @author jcm
     * @description TODO 构建返回成功的对象
     * TODO 类方法要先声明泛型
     * @date 2023/5/28 16:37
     */
    public static<T> CommonResult<T> success(T data){
        return new CommonResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    public static<T> CommonResult<T> success(T data,String message){
        return new CommonResult(ResultCode.SUCCESS.getCode(),message,data);
    }

    /**
     * @param iErrorCode:
     * @return CommonResult<T>
     * @author jcm
     * @description TODO 失败的返回结构
     * @date 2023/5/28 16:42
     */
    public static<T> CommonResult<T> failed(IErrorCode iErrorCode){
        return new CommonResult(iErrorCode.getCode(),iErrorCode.getMessage(),null);
    }

    public static<T> CommonResult<T> failed(String message){
        return new CommonResult(ResultCode.FAILED.getCode(),message,null);
    }

    public static<T> CommonResult<T> failed(){
        return CommonResult.failed(ResultCode.FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
