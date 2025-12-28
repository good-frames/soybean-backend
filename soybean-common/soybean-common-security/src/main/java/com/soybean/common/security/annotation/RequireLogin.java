package com.soybean.common.security.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录校验注解，基于Sa-Token的@SaCheckLogin扩展
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {
    String type() default "";

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
