package com.soybean.user.api.dto;

import com.soybean.user.api.enums.AdminUserStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员DTO
 *
 * @author soybean
 */
@Data
public class AdminUserDTO implements Serializable {

    /**
     * 管理员ID
     */
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（新增时需要）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态：0->禁用；1->启用
     */
    private AdminUserStatusEnum status;
}
