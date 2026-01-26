
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "用户和角色关联表")
public class SysUserRole implements Serializable {

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    @Schema(description = "角色ID")
    private Long roleId;
}
