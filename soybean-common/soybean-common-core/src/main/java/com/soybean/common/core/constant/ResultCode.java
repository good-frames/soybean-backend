package com.soybean.common.core.constant;

/**
 * 统一返回状态码常量
 *
 * @author soybean
 */
public interface ResultCode {

    /**
     * 操作成功
     */
    Integer SUCCESS = 200;

    /**
     * 参数错误
     */
    Integer PARAM_ERROR = 400;

    /**
     * 未授权,登录过期
     */
    Integer UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    Integer FORBIDDEN = 403;

    /**
     * 资源未找到
     */
    Integer NOT_FOUND = 404;

    /**
     * 服务器内部错误
     */
    Integer ERROR = 500;
}
