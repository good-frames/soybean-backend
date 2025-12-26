
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
public enum SysMenuCacheEnum implements BaseEnum<String> {
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

    SysMenuCacheEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysMenuCacheEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysMenuCacheEnum cacheEnum : values()) {
            if (cacheEnum.getValue().equals(value)) {
                return cacheEnum;
            }
        }
        throw new IllegalArgumentException("Unknown menu cache value: " + value);
    }
}
