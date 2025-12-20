package com.soybean.common.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
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
        this.success = code == 200;
    }

    public Result(Integer code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> ok() {
        return new Result<>(200, "操作成功");
    }

    /**
     * 成功返回结果
     *
     * @param data 返回的数据
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param msg  返回的消息
     * @param data 返回的数据
     */
    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> fail() {
        return new Result<>(500, "操作失败");
    }

    /**
     * 失败返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg);
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
        return new Result<>(400, msg);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(401, msg);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(401, "未授权");
    }

    /**
     * 禁止访问返回结果
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(403, "禁止访问");
    }

    /**
     * 禁止访问返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> forbidden(String msg) {
        return new Result<>(403, msg);
    }

    /**
     * 资源未找到返回结果
     */
    public static <T> Result<T> notFound() {
        return new Result<>(404, "资源未找到");
    }

    /**
     * 资源未找到返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> notFound(String msg) {
        return new Result<>(404, msg);
    }

    /**
     * 服务器内部错误返回结果
     */
    public static <T> Result<T> error() {
        return new Result<>(500, "服务器内部错误");
    }

    /**
     * 服务器内部错误返回结果
     *
     * @param msg 返回的消息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg);
    }
}
