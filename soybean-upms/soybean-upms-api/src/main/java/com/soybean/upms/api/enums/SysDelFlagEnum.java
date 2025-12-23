
package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 删除标志枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysDelFlagEnum implements BaseEnum<String> {
    /**
     * 存在
     */
    EXIST("0", "存在"),

    /**
     * 删除
     */
    DELETED("2", "删除");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysDelFlagEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysDelFlagEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysDelFlagEnum flagEnum : values()) {
            if (flagEnum.getValue().equals(value)) {
                return flagEnum;
            }
        }
        throw new IllegalArgumentException("Unknown del flag value: " + value);
    }
}
