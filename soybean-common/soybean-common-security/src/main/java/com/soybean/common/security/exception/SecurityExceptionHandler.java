package com.soybean.common.security.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.soybean.common.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 安全异常处理器
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    /**
     * 处理未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<Void> handleNotLoginException(NotLoginException e) {
        String message = "";
        switch (e.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = "未提供Token";
                break;
            case NotLoginException.INVALID_TOKEN:
                message = "Token无效";
                return Result.unauthorized(message);
            case NotLoginException.TOKEN_TIMEOUT:
                message = "Token已过期";
                break;
            case NotLoginException.BE_REPLACED:
                message = "Token已被顶下线";
                break;
            case NotLoginException.KICK_OUT:
                message = "Token已被踢下线";
                break;
            default:
                message = "当前会话未登录";
                break;
        }
        log.error("认证异常: {}", message, e);
        return Result.forbidden(message);
    }

    /**
     * 处理无权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result<Void> handleNotPermissionException(NotPermissionException e) {
        String message = "缺少权限:" + e.getPermission();
        log.error("权限异常: {}", message);
        return Result.forbidden(e.getMessage());
    }

    /**
     * 处理无角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<Void> handleNotRoleException(NotRoleException e) {
        String message = "缺少角色:" + e.getRole();
        log.error("角色异常: {}", message);
        return Result.forbidden(e.getMessage());
    }
}
