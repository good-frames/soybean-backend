package com.soybean.common.security.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录结果
 */
@Data
public class LoginResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 用户信息
     */
    private LoginUser userInfo;

    /**
     * 是否首次登录
     */
    private Boolean isFirstLogin;

    /**
     * 需要修改密码
     */
    private Boolean needChangePassword;
}
