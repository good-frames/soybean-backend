package com.soybean.user.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台管理员表
 * 该类对应数据库中的admin_user表，用于存储管理员信息
 * </p>
 *
 * @author dongdongjie
 * @since 2025-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_user")
public class AdminUser implements Serializable {

    /**
     * 管理员ID
     */
    @TableId(value = "uid", type = IdType.INPUT)
    private String uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
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
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}