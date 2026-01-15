
package com.soybean.common.core.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 状态枚举（0：停用；1：正常）
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum StatusEnum implements BaseEnum<String> {
    /**
     * 停用
     */
    DISABLE("0", "停用"),

    /**
     * 正常
     */
    NORMAL("1", "正常");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    StatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static StatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (StatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }
}
