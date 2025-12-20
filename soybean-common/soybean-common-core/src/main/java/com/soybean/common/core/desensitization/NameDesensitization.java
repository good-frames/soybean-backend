package com.soybean.common.core.desensitization;

/**
 * 姓名脱敏
 *
 * @author soybean
 * @date 2023-05-31
 */
public class NameDesensitization extends AbstractDesensitization {

    @Override
    public String desensitize(String origin) {
        if (origin == null) {
            return origin;
        }

        int length = origin.length();
        if (length == 0) {
            return origin;
        }

        if (length == 1) {
            return "*";
        }

        if (length == 2) {
            return origin.charAt(0) + "*";
        }

        // 保留第一个和最后一个字符，中间用*代替
        return origin.charAt(0) + "*".repeat(length - 2) + origin.charAt(length - 1);
    }
}
