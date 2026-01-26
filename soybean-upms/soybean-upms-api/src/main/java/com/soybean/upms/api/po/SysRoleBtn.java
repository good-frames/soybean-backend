package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色按钮关联实体
 *
 * @author soybean
 * @since 2024-07-12
 */
@Data
@TableName("sys_role_btn")
@Schema(description = "角色按钮关联实体")
public class SysRoleBtn {

    /**
     * 角色ID
     */
    @TableField("role_id")
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 按钮ID
     */
    @TableField("btn_id")
    @Schema(description = "按钮ID")
    private Long btnId;
}
