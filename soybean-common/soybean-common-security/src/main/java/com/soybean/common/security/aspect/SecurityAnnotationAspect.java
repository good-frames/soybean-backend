package com.soybean.common.security.aspect;

import cn.dev33.satoken.annotation.handler.SaCheckLoginHandler;
import cn.dev33.satoken.annotation.handler.SaCheckPermissionHandler;
import cn.dev33.satoken.annotation.handler.SaCheckRoleHandler;
import com.soybean.common.security.annotation.RequireLogin;
import com.soybean.common.security.annotation.RequirePermission;
import com.soybean.common.security.annotation.RequireRole;
import com.soybean.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 安全注解切面处理
 */
@Slf4j
@Aspect
@Component
public class SecurityAnnotationAspect {

    /**
     * 处理RequirePermission注解（方法级别）
     */
    @Around("@annotation(requirePermission)")
    public Object handleRequirePermissionMethod(ProceedingJoinPoint point, RequirePermission requirePermission) throws Throwable {
        return handleRequirePermissionInternal(point, requirePermission);
    }

    /**
     * 处理RequirePermission注解（类级别）
     */
    @Around("@within(requirePermission)")
    public Object handleRequirePermissionClass(ProceedingJoinPoint point, RequirePermission requirePermission) throws Throwable {
        return handleRequirePermissionInternal(point, requirePermission);
    }

    /**
     * 内部处理RequirePermission注解的通用方法
     */
    private Object handleRequirePermissionInternal(ProceedingJoinPoint point, RequirePermission requirePermission) throws Throwable {
        // 记录操作日志（如果需要）
        if (requirePermission.log()) {
            logOperation(point, requirePermission.module(), requirePermission.operation());
        }

        SaCheckPermissionHandler._checkMethod(requirePermission.type(), requirePermission.value(), requirePermission.mode(), requirePermission.orRole());

        // 执行原方法
        return point.proceed();
    }

    /**
     * 处理RequireRole注解（方法级别）
     */
    @Around("@annotation(requireRole)")
    public Object handleRequireRoleMethod(ProceedingJoinPoint point, RequireRole requireRole) throws Throwable {
        return handleRequireRoleInternal(point, requireRole);
    }

    /**
     * 处理RequireRole注解（类级别）
     */
    @Around("@within(requireRole)")
    public Object handleRequireRoleClass(ProceedingJoinPoint point, RequireRole requireRole) throws Throwable {
        return handleRequireRoleInternal(point, requireRole);
    }

    /**
     * 内部处理RequireRole注解的通用方法
     */
    private Object handleRequireRoleInternal(ProceedingJoinPoint point, RequireRole requireRole) throws Throwable {
        // 记录操作日志（如果需要）
        if (requireRole.log()) {
            logOperation(point, requireRole.module(), requireRole.operation());
        }

        SaCheckRoleHandler._checkMethod(requireRole.type(), requireRole.value(), requireRole.mode());

        // 执行原方法
        return point.proceed();
    }

    /**
     * 处理RequireLogin注解（方法级别）
     */
    @Around("@annotation(requireLogin)")
    public Object handleRequireLoginMethod(ProceedingJoinPoint point, RequireLogin requireLogin) throws Throwable {
        return handleRequireLoginInternal(point, requireLogin);
    }

    /**
     * 处理RequireLogin注解（类级别）
     */
    @Around("@within(requireLogin)")
    public Object handleRequireLoginClass(ProceedingJoinPoint point, RequireLogin requireLogin) throws Throwable {
        return handleRequireLoginInternal(point, requireLogin);
    }

    /**
     * 内部处理RequireLogin注解的通用方法
     */
    private Object handleRequireLoginInternal(ProceedingJoinPoint point, RequireLogin requireLogin) throws Throwable {
        // 记录操作日志（如果需要）
        if (requireLogin.log()) {
            logOperation(point, requireLogin.module(), requireLogin.operation());
        }


        SaCheckLoginHandler._checkMethod(requireLogin.type());

        // 执行原方法
        return point.proceed();
    }

    /**
     * 记录操作日志
     */
    private void logOperation(ProceedingJoinPoint point, String module, String operation) {
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            Object[] args = point.getArgs();

            log.info("操作日志 - 模块: {}, 操作: {}, 类: {}, 方法: {}, 参数: {}, 用户: {}", 
                module, 
                operation != null ? operation : methodName,
                className, 
                methodName, 
                Arrays.toString(args),
                SecurityUtil.isLogin() ? SecurityUtil.getUserId() : "未登录");
        } catch (Exception e) {
            log.error("记录操作日志失败", e);
        }
    }
}
