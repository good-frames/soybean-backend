package com.soybean.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置属性
 */
@Data
@ConfigurationProperties(prefix = "soybean.security")
public class SecurityProperties {

    /**
     * 是否启用安全模块
     */
    private boolean enabled = true;

    /**
     * Token配置
     */
    private Token token = new Token();

    /**
     * 登录配置
     */
    private Login login = new Login();

    /**
     * Token配置
     */
    @Data
    public static class Token {
        /**
         * Token名称
         */
        private String name = "satoken";

        /**
         * Token有效期（秒）
         */
        private long timeout = 2592000; // 30天

        /**
         * Token临时有效期（指定时间内无操作就视为token过期，单位：秒）
         */
        private long activeTimeout = -1;

        /**
         * 是否允许同一账号并发登录
         */
        private boolean concurrentLogin = true;

        /**
         * 在多人登录同一账号时，是否共用一个token
         */
        private boolean shareLogin = false;

        /**
         * Token风格
         */
        private String style = "uuid";
    }

    /**
     * 登录配置
     */
    @Data
    public static class Login {
        /**
         * 是否开启单设备登录
         */
        private boolean singleDevice = false;

        /**
         * 密码加密方式
         */
        private String passwordEncoder = "bcrypt";

        /**
         * 登录失败锁定次数
         */
        private int maxRetryCount = 5;

        /**
         * 登录失败锁定时间（分钟）
         */
        private int lockTime = 30;

        /**
         * 是否记录登录日志
         */
        private boolean logLogin = true;
    }
}
