package com.soybean.user.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.common.core.annotation.Desensitization;
import com.soybean.common.core.desensitization.EmailDesensitization;
import com.soybean.common.core.desensitization.NameDesensitization;
import com.soybean.common.core.desensitization.PhoneDesensitization;
import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.enums.SysUserGenderEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息VO（包含基本信息、角色和权限）
 *
 * @author soybean
 */
@Data
public class UserInfoVO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    @Desensitization(NameDesensitization.class)
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    @Desensitization(PhoneDesensitization.class)
    private String phone;

    /**
     * 邮箱
     */
    @Desensitization(EmailDesensitization.class)
    private String email;

    /**
     * 状态：0->禁用；1->启用
     */
    private SysUserStatusEnum status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 性别
     */
    private SysUserGenderEnum gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDate;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;
}
