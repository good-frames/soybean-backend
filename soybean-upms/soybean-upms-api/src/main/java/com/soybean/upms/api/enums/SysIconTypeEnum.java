package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 图标类型枚举
 *
 * @author soybean
 * @since 2024-07-07
 */
@Getter
public enum SysIconTypeEnum implements BaseEnum<String> {
    /**
     * 图标字体
     */
    ICONFONT("1", "iconify图标"),

    /**
     * SVG图标
     */
    SVG("2", "本地svg图标");


    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysIconTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysIconTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysIconTypeEnum typeEnum : values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown icon type value: " + value);
    }
}
