package com.soybean.common.security.annotation;

import cn.dev33.satoken.annotation.SaMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解，基于Sa-Token的@SaCheckPermission扩展
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    /* --------------------- 原@SaCheckPermission参数 --------------------- */
    /**
     * 权限类型
     */
    String type() default "";

    /**
     * 需要的权限标识
     */
    String[] value() default {};

    /**
     * 验证模式：AND（必须全部满足）或OR（满足其一即可）
     */
    SaMode mode() default SaMode.AND;

    /**
     * 需要的角色标识, 或者满足角色
     */
    String[] orRole() default {};
    /* --------------------- 原@SaCheckPermission参数 --------------------- */


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
