
package com.soybean.common.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 删除标志枚举（0：存在；1：删除）
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum DelFlagEnum implements BaseEnum<String> {
    /**
     * 存在
     */
    EXIST("0", "存在"),

    /**
     * 删除
     */
    DELETED("1", "删除");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    DelFlagEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static DelFlagEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (DelFlagEnum flagEnum : values()) {
            if (flagEnum.getValue().equals(value)) {
                return flagEnum;
            }
        }
        throw new IllegalArgumentException("Unknown del flag value: " + value);
    }
}
