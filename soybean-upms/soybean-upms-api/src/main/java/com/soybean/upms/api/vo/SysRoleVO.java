
package com.soybean.upms.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.common.core.enums.StatusEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class SysRoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
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
    private StatusEnum status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 菜单权限列表
     */
    private List<Long> menuIds;

    /**
     * 是否为超级管理员
     */
    private Boolean admin;

    /**
     * 角色的默认首页路由名称
     */
    private String home;
}
