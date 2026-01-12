
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.upms.api.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * 该类对应数据库中的sys_menu表，用于存储系统菜单权限信息
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu implements Serializable {

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    @TableField("route_name")
    private String routeName;

    /**
     * 菜单标题
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 国际化键
     */
    private String i18nKey;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField("`order`")
    private Integer order;

    /**
     * 路由地址
     */
    @TableField("route_path")
    private String routePath;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链（false不是外链 true是外链）
     */
    @TableField("is_frame")
    private Boolean isFrame;

    /**
     * 外链地址
     */
    private String href;

    /**
     * 是否缓存（false不缓存 true缓存）
     */
    @TableField("keep_alive")
    private Boolean keepAlive;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField("menu_type")
    private SysMenuTypeEnum menuType;

    /**
     * 菜单是否在菜单中隐藏（false不隐藏 true隐藏）
     */
    @TableField("hide_in_menu")
    private Boolean hideInMenu;

    /**
     * 菜单状态（0停用 1正常）
     */
    @TableField("status")
    private SysMenuStatusEnum status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 图标类型
     */
    @TableField("icon_type")
    private SysIconTypeEnum iconType;

    /**
     * 是否为静态菜单（0否 1是）
     */
    @TableField("constant")
    private SysMenuConstantEnum constant;

    /**
     * 是否多页签（0否 1是）
     */
    @TableField("multi_tab")
    private SysMenuMultiTabEnum multiTab;

    /**
     * 高亮路由名称
     */
    @TableField("active_menu")
    private String activeMenu;

    /**
     * 菜单在标签页中的顺序
     */
    @TableField("fixed_index_in_tab")
    private Integer fixedIndexInTab;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
