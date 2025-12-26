
package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单是否为外链枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuFrameEnum implements BaseEnum<String> {
    /**
     * 不是外链
     */
    NO("0", "不是外链"),

    /**
     * 是外链
     */
    YES("1", "是外链");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysMenuFrameEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuFrameEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuFrameEnum frameEnum : values()) {
            if (frameEnum.getValue().equals(value)) {
                return frameEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu frame value: " + value);
    }
}
