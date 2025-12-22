package com.soybean.common.security.constant;

/**
 * 安全相关常量
 */
public class SecurityConstant {
    /**
     * 用户信息在Session中的键名
     */
    public static final String USER_SESSION_KEY = "loginUser";

    /**
     * Token名称
     */
    public static final String TOKEN_NAME = "satoken";

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 无需认证的路径
     */
    public static final String[] EXCLUDE_PATH_PATTERNS = {
        "/login",
        "/logout",
        "/captcha",
        "/error",
        "/actuator/**"
    };
}
