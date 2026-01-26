package com.soybean.upms.api.query;

import com.soybean.common.mybatis.query.PageQuery;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单树查询对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Schema(description = "菜单树查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuTreeQuery extends PageQuery implements Serializable {

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")
    private String routeName;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Schema(description = "菜单状态：0正常 1停用")
    private StatusEnum status;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Schema(description = "菜单类型：M目录 C菜单 F按钮")
    private SysMenuTypeEnum menuType;
}
