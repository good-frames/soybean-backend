package com.soybean.common.core.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用 BaseEnum 转换器工厂
 * 支持前端传 数字/字符串 转任意 BaseEnum
 */
@Component   // 扔进容器即生效
public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>> {

    private static final Map<Class<?>, Converter<String, ? extends BaseEnum<?>>> CACHE = new HashMap<>();

    @NonNull
    @Override
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        //noinspection unchecked
        return (Converter<String, T>) CACHE.computeIfAbsent(targetType, type ->
                new Converter<String, BaseEnum<?>>() {
                    @Override
                    public BaseEnum<?> convert(String source) {
                        if (source.isBlank()) return null;
                        
                        // 1. 先尝试按字符串值匹配
                        for (BaseEnum<?> e : ((Class<BaseEnum<?>>) type).getEnumConstants()) {
                            if (e.getValue().toString().equals(source)) return e;
                        }
                        
                        // 2. 尝试按枚举名匹配
                        for (BaseEnum<?> e : ((Class<BaseEnum<?>>) type).getEnumConstants()) {
                            String enumName = ((Enum<?>) e).name();
                            if (enumName.equalsIgnoreCase(source)) return e;
                        }
                        
                        // 3. 尝试按数字匹配（兼容旧的Integer类型枚举）
                        try {
                            int val = Integer.parseInt(source);
                            for (BaseEnum<?> e : ((Class<BaseEnum<?>>) type).getEnumConstants()) {
                                if (e.getValue() instanceof Integer && e.getValue().equals(val)) return e;
                            }
                        } catch (NumberFormatException ignore) {
                            // 忽略数字解析错误
                        }
                        
                        throw new IllegalArgumentException("Unknown value " + source + " for " + type.getSimpleName());
                    }
                });
    }
}