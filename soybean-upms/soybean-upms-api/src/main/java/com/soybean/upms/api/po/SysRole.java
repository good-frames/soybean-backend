
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.upms.api.enums.SysRoleStatusEnum;
import com.soybean.upms.api.enums.SysDelFlagEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色信息表
 * 该类对应数据库中的sys_role表，用于存储系统角色信息
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole implements Serializable {

    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0停用 1正常）
     */
    @TableField("status")
    private SysRoleStatusEnum status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    private SysDelFlagEnum delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
