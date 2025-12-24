package com.soybean.common.core.exception;

import com.soybean.common.core.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author soybean
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数验证失败: {}", e.getMessage());
        return Result.paramError(e.getMessage());
    }

    /** 业务异常（自己抛的） */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.fail(e.getMessage());
    }
    
    /**
     * 处理类型转换异常
     */
    @ExceptionHandler(org.springframework.core.convert.ConversionFailedException.class)
    public Result<Void> handleConversionFailedException(org.springframework.core.convert.ConversionFailedException e) {
        log.error("类型转换失败: {}", e.getMessage());
        return Result.paramError("参数类型错误: " + e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return Result.error("系统异常: " + e.getMessage());
    }
}