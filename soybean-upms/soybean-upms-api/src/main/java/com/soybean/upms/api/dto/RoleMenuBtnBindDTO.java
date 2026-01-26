package com.soybean.upms.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单按钮绑定数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "角色菜单按钮绑定数据传输对象")
public class RoleMenuBtnBindDTO implements Serializable {

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