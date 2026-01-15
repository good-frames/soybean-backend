package com.soybean.upms.api.dto;

import com.soybean.upms.api.enums.SysUserGenderEnum;
import com.soybean.common.core.enums.StatusEnum;
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
    private String id;

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
    private StatusEnum status;

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
