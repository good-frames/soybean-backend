package com.soybean.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义验证注解，用于标记需要使用特定验证器验证的参数
 *
 * @author soybean
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedBy {
    /**
     * 验证器的Bean名称
     */
    Class<?> value();
    
    /**
     * 验证方法名称，默认为"validate"
     */
    String method() default "validate";
}