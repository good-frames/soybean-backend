package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 按钮权限表
 * 该类对应数据库中的sys_btn表，用于存储菜单下的按钮权限信息
 * </p>
 *
 * @author soybean
 * @since 2026-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_btn")
@Schema(description = "按钮权限表")
public class SysBtn implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 按钮ID
     */
    @TableId(value = "btn_id", type = IdType.AUTO)
    @Schema(description = "按钮ID")
    private Long id;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long menuId;

    /**
     * 按钮标识
     */
    @Schema(description = "按钮标识")
    private String btnCode;

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
}
