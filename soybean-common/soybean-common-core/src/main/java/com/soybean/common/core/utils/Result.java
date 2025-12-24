package com.soybean.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.soybean.common.core.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 *
 * @author soybean
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功标记
     */
    private Boolean success;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
        this.success = code.equals(ResultCode.SUCCESS);
    }

    public Result(Integer code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> ok() {
        return new Result<>(ResultCode.SUCCESS, "操作成功");
    }

    /**
     * 成功返回结果
     *
     * @param data 返回的数据
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.SUCCESS, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param msg  返回的消息
     * @param data 返回的数据
     */
    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(ResultCode.SUCCESS, msg, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.ERROR, "操作失败");
    }

    /**
     * 失败返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.PARAM_ERROR, msg);
    }

    /**
     * 失败返回结果
     *
     * @param code 状态码
     * @param msg  返回的消息
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 失败返回结果
     *
     * @param code 状态码
     * @param msg  返回的消息
     * @param data 返回的数据
     */
    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    /**
     * 参数校验失败返回结果
     */
    public static <T> Result<T> paramError(String msg) {
        return new Result<>(ResultCode.PARAM_ERROR, msg);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(ResultCode.UNAUTHORIZED, msg);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(ResultCode.UNAUTHORIZED, "未授权");
    }

    /**
     * 禁止访问返回结果
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(ResultCode.FORBIDDEN, "禁止访问");
    }

    /**
     * 禁止访问返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> forbidden(String msg) {
        return new Result<>(ResultCode.FORBIDDEN, msg);
    }

    /**
     * 资源未找到返回结果
     */
    public static <T> Result<T> notFound() {
        return new Result<>(ResultCode.NOT_FOUND, "资源未找到");
    }

    /**
     * 资源未找到返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> notFound(String msg) {
        return new Result<>(ResultCode.NOT_FOUND, msg);
    }

    /**
     * 服务器内部错误返回结果
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.ERROR, "服务器内部错误");
    }

    /**
     * 服务器内部错误返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCode.ERROR, msg);
    }

    /**
     * 判断请求是否成功
     *
     * @return 是否成功
     */
    public Boolean isSuccess() {
        return this.success != null && this.success;
    }
}
