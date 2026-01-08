package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单是否在菜单中隐藏枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuHideInMenuEnum implements BaseEnum<String> {
    /**
     * 不隐藏
     */
    NOT_HIDE("0", "不隐藏"),

    /**
     * 隐藏
     */
    HIDE("1", "隐藏");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysMenuHideInMenuEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuHideInMenuEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuHideInMenuEnum hideInMenuEnum : values()) {
            if (hideInMenuEnum.getValue().equals(value)) {
                return hideInMenuEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu hide in menu value: " + value);
    }
}