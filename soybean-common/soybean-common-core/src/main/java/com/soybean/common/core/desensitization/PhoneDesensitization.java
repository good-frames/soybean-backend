package com.soybean.common.core.desensitization;

/**
 * 手机号脱敏
 *
 * @author soybean
 * @date 2023-05-31
 */
public class PhoneDesensitization extends AbstractDesensitization {

    @Override
    public String desensitize(String origin) {
        if (origin == null || origin.length() < 11) {
            return origin;
        }
        return origin.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
