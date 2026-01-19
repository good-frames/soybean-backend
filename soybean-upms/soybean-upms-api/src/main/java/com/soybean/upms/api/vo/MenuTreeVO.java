package com.soybean.upms.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单分页视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTreeVO extends SysMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子菜单
     */
    private List<MenuTreeVO> children;



    /**
     * 按钮列表
     */
    private List<SysBtnVO> buttons;
}
