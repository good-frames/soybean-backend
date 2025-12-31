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


/**
 * 管理员VO
 *
 * @author soybean
 */
@Data
public class SysUserVO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    @Desensitization(NameDesensitization.class)
    private String username;

    /**
     * 昵称
     */
    private String nickname;

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
     * 状态名称：禁用、启用
     */
    private String statusName;
    
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
}
