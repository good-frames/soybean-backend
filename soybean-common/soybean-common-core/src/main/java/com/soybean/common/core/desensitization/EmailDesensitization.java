package com.soybean.common.core.desensitization;

/**
 * 邮箱脱敏
 *
 * @author soybean
 * @date 2023-05-31
 */
public class EmailDesensitization extends AbstractDesensitization {

    @Override
    public String desensitize(String origin) {
        if (origin == null) {
            return origin;
        }

        int index = origin.indexOf("@");
        if (index <= 1) {
            return origin;
        }

        String prefix = origin.substring(0, index);
        String suffix = origin.substring(index);

        if (prefix.length() <= 3) {
            return prefix.charAt(0) + "***" + suffix;
        } else {
            return prefix.substring(0, 3) + "***" + suffix;
        }
    }
}
