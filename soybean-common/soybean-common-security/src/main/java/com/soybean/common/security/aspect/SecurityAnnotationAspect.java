package com.soybean.common.security.aspect;

import com.soybean.common.security.annotation.RequireLogin;
import com.soybean.common.security.annotation.RequirePermission;
import com.soybean.common.security.annotation.RequireRole;
import com.soybean.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 安全注解切面处理
 */
@Slf4j
@Aspect
@Component
@Order(1) // 确保在Sa-Token的拦截器之前执行
public class SecurityAnnotationAspect {

    /**
     * 处理RequirePermission注解
     */
    @Around("@annotation(requirePermission)")
    public Object handleRequirePermission(ProceedingJoinPoint point, RequirePermission requirePermission) throws Throwable {
        // 记录操作日志（如果需要）
        if (requirePermission.log()) {
            logOperation(point, requirePermission.module(), requirePermission.operation());
        }

        // 执行原方法
        return point.proceed();
    }

    /**
     * 处理RequireRole注解
     */
    @Around("@annotation(requireRole)")
    public Object handleRequireRole(ProceedingJoinPoint point, RequireRole requireRole) throws Throwable {
        // 记录操作日志（如果需要）
        if (requireRole.log()) {
            logOperation(point, requireRole.module(), requireRole.operation());
        }

        // 执行原方法
        return point.proceed();
    }

    /**
     * 处理RequireLogin注解
     */
    @Around("@annotation(requireLogin)")
    public Object handleRequireLogin(ProceedingJoinPoint point, RequireLogin requireLogin) throws Throwable {
        // 记录操作日志（如果需要）
        if (requireLogin.log()) {
            logOperation(point, requireLogin.module(), requireLogin.operation());
        }

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
