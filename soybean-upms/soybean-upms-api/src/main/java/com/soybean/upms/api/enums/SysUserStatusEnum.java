package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 系统用户状态枚举
 *
 * @author soybean
 */
@Getter
public enum SysUserStatusEnum implements BaseEnum<String> {

    /**
     * 正常
     */
    NORMAL("1", "正常"),

    /**
     * 停用
     */
    DISABLE("0", "停用");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysUserStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysUserStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysUserStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }


}
