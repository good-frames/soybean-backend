package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 是否枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysMenuConstantEnum implements BaseEnum<String> {
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

    SysMenuConstantEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuConstantEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuConstantEnum yesNoEnum : values()) {
            if (yesNoEnum.getValue().equals(value)) {
                return yesNoEnum;
            }
        }
        throw new IllegalArgumentException("Unknown yes/no value: " + value);
    }
}
