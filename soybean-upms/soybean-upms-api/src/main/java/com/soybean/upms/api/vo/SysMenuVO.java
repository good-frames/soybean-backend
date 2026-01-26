
package com.soybean.upms.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import com.soybean.upms.api.enums.SysIconTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "菜单视图对象")
public class SysMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String routeName;

    /**
     * 菜单标题
     */
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
    @Schema(description = "显示顺序")
    private Integer order;

    /**
     * 路由地址
     */
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
    @Schema(description = "是否为外链")
    private boolean isFrame;

    /**
     * 外链地址
     */
    @Schema(description = "外链地址")
    private String href;

    /**
     * 是否缓存（false不缓存 true缓存）
     */
    @Schema(description = "是否缓存")
    private boolean keepAlive;

    /**
     * 菜单类型（M目录 C菜单）
     */
    @Schema(description = "菜单类型")
    private SysMenuTypeEnum menuType;

    /**
     * 菜单是否在菜单中隐藏（false不隐藏 true隐藏）
     */
    @Schema(description = "菜单是否在菜单中隐藏")
    private boolean hideInMenu;

    /**
     * 菜单状态（0停用 1正常）
     */
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
    @Schema(description = "图标类型")
    private SysIconTypeEnum iconType;

    /**
     * 是否为静态菜单（false否 true是）
     */
    @Schema(description = "是否为静态菜单")
    private Boolean constant;

    /**
     * 是否多页签（false否 true是）
     */
    @Schema(description = "是否多页签")
    private Boolean multiTab;

    /**
     * 高亮路由名称
     */
    @Schema(description = "高亮路由名称")
    private String activeMenu;

    /**
     * 菜单在标签页中的顺序
     */
    @Schema(description = "菜单在标签页中的顺序")
    private Integer fixedIndexInTab;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
