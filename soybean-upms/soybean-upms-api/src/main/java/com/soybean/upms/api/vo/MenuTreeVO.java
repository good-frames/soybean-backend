package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单分页视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单树视图对象")
@Data
public class MenuTreeVO extends SysMenuVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单列表")
    private List<MenuTreeVO> children;



    /**
     * 按钮列表
     */
    @Schema(description = "按钮列表")
    private List<SysBtnVO> buttons;
}
