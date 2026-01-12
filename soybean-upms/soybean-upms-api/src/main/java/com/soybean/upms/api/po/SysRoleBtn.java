package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色按钮关联实体
 *
 * @author soybean
 * @since 2024-07-12
 */
@Data
@TableName("sys_role_btn")
public class SysRoleBtn {

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 按钮ID
     */
    @TableField("btn_id")
    private Long btnId;
}
