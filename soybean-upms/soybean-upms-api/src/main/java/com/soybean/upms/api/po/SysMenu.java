
package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "菜单权限表")
public class SysMenu implements Serializable {

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 菜单名称
     */
    @TableField("route_name")
    @Schema(description = "菜单名称")
    private String routeName;

    /**
     * 菜单标题
     */
    @TableField("menu_name")
    @Schema(description = "菜单标题")
    private String menuName;

    /**
     * 国际化键
     */
    @Schema(description = "国际化键")
    private String i18nKey;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField("`order`")
    @Schema(description = "显示顺序")
    private Integer order;

    /**
     * 路由地址
     */
    @TableField("route_path")
    @Schema(description = "路由地址")
    private String routePath;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String query;

    /**
     * 是否为外链（false不是外链 true是外链）
     */
    @TableField("is_frame")
    @Schema(description = "是否为外链")
    private Boolean isFrame;

    /**
     * 外链地址
     */
    @Schema(description = "外链地址")
    private String href;

    /**
     * 是否缓存（false不缓存 true缓存）
     */
    @TableField("keep_alive")
    @Schema(description = "是否缓存")
    private Boolean keepAlive;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField("menu_type")
    @Schema(description = "菜单类型")
    private SysMenuTypeEnum menuType;

    /**
     * 菜单是否在菜单中隐藏（false不隐藏 true隐藏）
     */
    @TableField("hide_in_menu")
    @Schema(description = "菜单是否在菜单中隐藏")
    private Boolean hideInMenu;

    /**
     * 菜单状态（0停用 1正常）
     */
    @TableField("status")
    @Schema(description = "菜单状态")
    private StatusEnum status;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 图标类型
     */
    @TableField("icon_type")
    @Schema(description = "图标类型")
    private SysIconTypeEnum iconType;

    /**
     * 是否为静态菜单（0否 1是）
     */
    @TableField("constant")
    @Schema(description = "是否为静态菜单")
    private SysMenuConstantEnum constant;

    /**
     * 是否多页签（0否 1是）
     */
    @TableField("multi_tab")
    @Schema(description = "是否多页签")
    private SysMenuMultiTabEnum multiTab;

    /**
     * 高亮路由名称
     */
    @TableField("active_menu")
    @Schema(description = "高亮路由名称")
    private String activeMenu;

    /**
     * 菜单在标签页中的顺序
     */
    @TableField("fixed_index_in_tab")
    @Schema(description = "菜单在标签页中的顺序")
    private Integer fixedIndexInTab;

    /**
     * 创建者
     */
    @TableField("create_by")
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    @Schema(description = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
