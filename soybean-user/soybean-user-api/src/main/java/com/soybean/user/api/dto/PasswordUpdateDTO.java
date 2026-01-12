package com.soybean.user.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 密码修改DTO
 *
 * @author soybean
 */
@Data
public class PasswordUpdateDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
