
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * 该类对应数据库中的sys_role_menu表，用于存储角色和菜单的关联关系
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Accessors(chain = true)
@TableName("sys_role_menu")
@Schema(description = "角色和菜单关联表")
public class SysRoleMenu implements Serializable {

    /**
     * 角色ID
     */
    @TableField("role_id")
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    @Schema(description = "菜单ID")
    private Long menuId;
}
