package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单多页签枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuMultiTabEnum implements BaseEnum<String> {
    /**
     * 否
     */
    NO("0", "否"),

    /**
     * 是
     */
    YES("1", "是");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysMenuMultiTabEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuMultiTabEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuMultiTabEnum multiTabEnum : values()) {
            if (multiTabEnum.getValue().equals(value)) {
                return multiTabEnum;
            }
        }
        throw new IllegalArgumentException("Unknown multi-tab value: " + value);
    }
}
