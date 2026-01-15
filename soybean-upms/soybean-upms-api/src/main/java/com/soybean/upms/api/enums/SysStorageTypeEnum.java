package com.soybean.upms.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.soybean.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 存储类型枚举
 *
 * @author soybean
 */
@Getter
public enum SysStorageTypeEnum implements BaseEnum<String> {

    /**
     * 阿里云OSS
     */
    AliOss("AliOss", "阿里云OSS"),

    /**
     * 七牛云
     */
    QiNiuCloud("QiNiuCloud", "七牛云"),

    /**
     * 腾讯云COS
     */
    QCloudCos("QCloudCos", "腾讯云COS"),

    /**
     * MinIO
     */
    Minio("Minio", "MinIO");

    @EnumValue
    @JsonValue
    private final String value;

    private final String desc;

    SysStorageTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SysStorageTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SysStorageTypeEnum typeEnum : values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown storage type value: " + value);
    }
}
