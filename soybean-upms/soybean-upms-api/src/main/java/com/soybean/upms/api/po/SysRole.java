
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.common.core.enums.DelFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "角色信息表")
public class SysRole implements Serializable {

    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @Schema(description = "角色权限字符串")
    private String roleKey;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer roleSort;

    /**
     * 角色状态（0停用 1正常）
     */
    @TableField("status")
    @Schema(description = "角色状态")
    private StatusEnum status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    @Schema(description = "删除标志")
    private DelFlagEnum delFlag;

    /**
     * 创建者
     */
    @TableField("create_by")
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    @Schema(description = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 角色的默认首页路由名称
     */
    @Schema(description = "角色的默认首页路由名称")
    private String home;
}
