
package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuTypeEnum implements BaseEnum<String> {
    /**
     * 目录
     */
    DIRECTORY("M", "目录"),

    /**
     * 菜单
     */
    MENU("C", "菜单");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysMenuTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuTypeEnum typeEnum : values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu type value: " + value);
    }
}
