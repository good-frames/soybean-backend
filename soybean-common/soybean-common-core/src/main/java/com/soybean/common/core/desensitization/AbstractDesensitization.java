package com.soybean.common.core.desensitization;

/**
 * 数据脱敏抽象类
 *
 * @author soybean
 * @date 2023-05-31
 */
public abstract class AbstractDesensitization {

    /**
     * 脱敏处理
     *
     * @param origin 原始字符串
     * @return 脱敏后的字符串
     */
    public abstract String desensitize(String origin);
}
