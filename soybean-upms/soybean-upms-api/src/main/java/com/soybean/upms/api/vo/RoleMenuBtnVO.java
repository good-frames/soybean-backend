package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单按钮VO
 *
 * @author soybean
 * @since 2024-07-12
 */
@Data
@Schema(description = "角色菜单按钮视图对象")
public class RoleMenuBtnVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID列表
     */
    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;

    /**
     * 按钮ID列表
     */
    @Schema(description = "按钮ID列表")
    private List<Long> btnIds;
}
