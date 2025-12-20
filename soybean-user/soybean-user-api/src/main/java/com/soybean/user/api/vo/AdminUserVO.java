package com.soybean.user.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.common.core.annotation.Desensitization;
import com.soybean.common.core.desensitization.EmailDesensitization;
import com.soybean.common.core.desensitization.NameDesensitization;
import com.soybean.common.core.desensitization.PhoneDesensitization;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 管理员VO
 *
 * @author soybean
 */
@Data
public class AdminUserVO {

    /**
     * 管理员ID
     */
    private String uid;

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
    private AdminUserStatusEnum status;
    
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
}
