package com.soybean.upms.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单分页视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class MenuTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 菜单状态
     */
    private String status;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 国际化键
     */
    private String i18nKey;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 图标类型
     */
    private String iconType;

    /**
     * 是否多标签页
     */
    private Boolean multiTab;

    /**
     * 是否在菜单中隐藏
     */
    private Boolean hideInMenu;

    /**
     * 激活菜单
     */
    private String activeMenu;

    /**
     * 子菜单
     */
    private List<MenuTreeVO> children;
}
