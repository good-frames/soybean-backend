package com.soybean.common.core.config;

import com.soybean.common.core.aspect.ValidationAspect;
import com.soybean.common.core.enums.BaseEnumConverterFactory;
import com.soybean.common.core.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用核心模块自动配置类
 *
 * @author soybean
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.soybean.common.core")
public class CommonCoreAutoConfiguration implements WebMvcConfigurer {

    /**
     * 注册验证切面
     */
    @Bean
    public ValidationAspect validationAspect(ApplicationContext applicationContext) {
        return new ValidationAspect(applicationContext);
    }

    /**
     * 注册全局异常处理器
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
    
    /**
     * 注册枚举转换器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new BaseEnumConverterFactory());
    }
}
