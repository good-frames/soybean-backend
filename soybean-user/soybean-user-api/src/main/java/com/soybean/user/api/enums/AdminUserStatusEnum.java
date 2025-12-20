package com.soybean.user.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 管理员状态枚举
 *
 * @author soybean
 */
@Getter
public enum AdminUserStatusEnum implements BaseEnum<Integer> {

    /**
     * 禁用
     */
    DISABLED(0, "禁用"),

    /**
     * 启用
     */
    ENABLED(1, "启用");

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String desc;

    AdminUserStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static AdminUserStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (AdminUserStatusEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }
}
