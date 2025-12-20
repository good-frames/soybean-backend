package com.soybean.common.core.aspect;

import com.soybean.common.core.annotation.ValidatedBy;
import lombok.RequiredArgsConstructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

/**
 * 验证切面，处理@ValidatedBy注解
 *
 * @author soybean
 */
@Aspect
@RequiredArgsConstructor
public class ValidationAspect {

    private final ApplicationContext applicationContext;

    @Around("execution(* com.soybean.user.controller.*.*(..))")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        // 获取参数注解
        java.lang.reflect.Parameter[] parameters = signature.getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            ValidatedBy annotation = parameters[i].getAnnotation(ValidatedBy.class);
            if (annotation != null) {
                // 获取验证器
                Object validator = applicationContext.getBean(annotation.value());

                // 执行验证
                Object target = args[i];
                String name = parameters[i].getName();
                Errors errors = new BeanPropertyBindingResult(target, name);
                
                // 获取指定的验证方法
                String methodName = annotation.method();
                try {
                    Method validateMethod;
                    if ("validate".equals(methodName) && validator instanceof Validator) {
                        // 如果是默认的validate方法且验证器实现了Validator接口
                        ((Validator) validator).validate(target, errors);
                    } else {
                        // 使用反射调用指定的验证方法
                        validateMethod = validator.getClass().getMethod(methodName, Object.class, Errors.class);
                        validateMethod.invoke(validator, target, errors);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("调用验证方法失败: " + methodName, e);
                }

                // 如果有错误，抛出异常
                if (errors.hasErrors()) {
                    String errorMsg = errors.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining(", "));
                    throw new IllegalArgumentException(errorMsg);
                }
            }
        }
        return joinPoint.proceed();
    }
}