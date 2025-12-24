package com.soybean.auth.domain.vo;

import lombok.Data;

/**
 * 登录响应VO
 *
 * @author soybean
 */
@Data
public class LoginVO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;

    public LoginVO(String accessToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
