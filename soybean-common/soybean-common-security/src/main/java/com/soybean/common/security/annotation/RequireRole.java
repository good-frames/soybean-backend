package com.soybean.common.security.annotation;

import cn.dev33.satoken.annotation.SaCheckRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色校验注解，基于Sa-Token的@SaCheckRole扩展
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SaCheckRole  // 组合Sa-Token原生注解
public @interface RequireRole {

    /**
     * 需要的角色标识
     */
    String[] value() default {};

    /**
     * 验证模式：AND（必须全部满足）或OR（满足其一即可）
     */
    cn.dev33.satoken.annotation.SaMode mode() default cn.dev33.satoken.annotation.SaMode.AND;

    /**
     * 操作描述，用于日志记录
     */
    String operation() default "";

    /**
     * 模块名称，用于日志分类
     */
    String module() default "";

    /**
     * 是否记录操作日志
     */
    boolean log() default true;
}
