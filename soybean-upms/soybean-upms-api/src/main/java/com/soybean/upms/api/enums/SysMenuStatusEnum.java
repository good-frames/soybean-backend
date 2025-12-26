
package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单状态枚举（0：停用；1：正常）
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuStatusEnum implements BaseEnum<String> {
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

    SysMenuStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu status value: " + value);
    }
}
