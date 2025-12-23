
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户和角色关联表
 * 该类对应数据库中的sys_user_role表，用于存储用户和角色的关联关系
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
}
