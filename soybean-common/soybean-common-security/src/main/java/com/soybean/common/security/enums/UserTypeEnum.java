package com.soybean.common.security.enums;

/**
 * 用户类型枚举
 */
public enum UserTypeEnum {
    /**
     * 管理员
     */
    ADMIN("admin", "管理员"),

    /**
     * 普通用户
     */
    USER("user", "普通用户");

    private final String code;
    private final String description;

    UserTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
