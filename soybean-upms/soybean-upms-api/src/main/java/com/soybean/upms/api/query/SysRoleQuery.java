
package com.soybean.upms.api.query;

import com.soybean.common.mybatis.query.PageQuery;
import com.soybean.common.core.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleQuery extends PageQuery {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0停用 1正常）
     */
    private StatusEnum status;

    /**
     * 时间范围-开始时间
     */
    private String beginTime;

    /**
     * 时间范围-结束时间
     */
    private String endTime;
}
