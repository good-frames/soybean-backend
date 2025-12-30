package com.soybean.upms.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单树VO（前端路由格式）
 *
 * @author soybean
 */
@Data
public class MenuTreeVO {

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 子菜单
     */
    private List<MenuTreeVO> children;

    /**
     * 元数据
     */
    private MenuMetaVO meta;
}
