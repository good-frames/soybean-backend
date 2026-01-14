package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 用户性别枚举
 *
 * @author soybean
 */
@Getter
public enum SysUserGenderEnum implements BaseEnum<String> {

    /**
     * 男
     */
    MALE("1", "男"),

    /**
     * 女
     */
    FEMALE("0", "女"),

    /**
     * 未知
     */
    UNKNOWN("2", "未知");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysUserGenderEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysUserGenderEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysUserGenderEnum genderEnum : values()) {
            if (genderEnum.getValue().equals(value)) {
                return genderEnum;
            }
        }
        throw new IllegalArgumentException("Unknown gender value: " + value);
    }
}
