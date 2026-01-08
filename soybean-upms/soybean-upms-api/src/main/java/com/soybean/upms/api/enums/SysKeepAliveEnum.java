package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 菜单是否缓存枚举（0：不缓存；1：缓存）
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysKeepAliveEnum implements BaseEnum<String> {
    /**
     * 不缓存
     */
    NO_CACHE("0", "不缓存"),

    /**
     * 缓存
     */
    CACHE("1", "缓存");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysKeepAliveEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysKeepAliveEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysKeepAliveEnum keepAliveEnum : values()) {
            if (keepAliveEnum.getValue().equals(value)) {
                return keepAliveEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu keep alive value: " + value);
    }
}