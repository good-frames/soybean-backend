package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单是否可见枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuVisibleEnum implements BaseEnum<String> {
    /**
     * 可见
     */
    VISIBLE("0", "可见"),

    /**
     * 隐藏
     */
    HIDDEN("1", "隐藏");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysMenuVisibleEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuVisibleEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuVisibleEnum visibleEnum : values()) {
            if (visibleEnum.getValue().equals(value)) {
                return visibleEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu visible value: " + value);
    }
}
