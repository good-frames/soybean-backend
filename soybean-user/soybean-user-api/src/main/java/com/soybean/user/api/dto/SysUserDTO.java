package com.soybean.user.api.dto;

import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.enums.SysUserGenderEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员DTO
 *
 * @author soybean
 */
@Data
public class SysUserDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 昵称
     */
    private String nickName;

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
    private SysUserStatusEnum status;

    /**
     * 性别
     */
    private SysUserGenderEnum gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 登录IP（用于登录时记录）
     */
    private String loginIp;
}
