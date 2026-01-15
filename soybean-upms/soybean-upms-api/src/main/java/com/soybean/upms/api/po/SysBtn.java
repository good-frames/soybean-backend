package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;

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
public class SysBtn implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 按钮ID
     */
    @TableId(value = "btn_id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 按钮标识
     */
    private String btnCode;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
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
