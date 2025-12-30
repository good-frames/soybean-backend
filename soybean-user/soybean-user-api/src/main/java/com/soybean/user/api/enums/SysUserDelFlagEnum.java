package com.soybean.user.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 用户删除标记枚举
 *
 * @author soybean
 */
@Getter
public enum SysUserDelFlagEnum implements BaseEnum<String> {

    /**
     * 正常
     */
    NORMAL("0", "正常"),

    /**
     * 删除
     */
    DELETED("1", "删除");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysUserDelFlagEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysUserDelFlagEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysUserDelFlagEnum delFlagEnum : values()) {
            if (delFlagEnum.getValue().equals(value)) {
                return delFlagEnum;
            }
        }
        throw new IllegalArgumentException("Unknown del_flag value: " + value);
    }
}
