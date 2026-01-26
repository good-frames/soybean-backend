
package com.soybean.upms.api.dto;

import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "菜单数据传输对象")
public class SysMenuDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @Schema(description = "菜单名称", required = true)
    private String routeName;

    /**
     * 菜单标题
     */
    @Size(max = 50, message = "菜单标题长度不能超过50个字符")
    @Schema(description = "菜单标题")
    private String menuName;

    /**
     * 国际化键
     */
    @Size(max = 100, message = "国际化键长度不能超过100个字符")
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
    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "显示顺序", required = true)
    private Integer order;

    /**
     * 路由地址
     */
    @Size(max = 200, message = "路由地址长度不能超过200个字符")
    @Schema(description = "路由地址")
    private String routePath;

    /**
     * 组件路径
     */
    @Size(max = 255, message = "组件路径长度不能超过255个字符")
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Size(max = 255, message = "路由参数长度不能超过255个字符")
    @Schema(description = "路由参数")
    private String query;

    /**
     * 是否为外链（false不是外链 true是外链）
     */
    @Schema(description = "是否为外链")
    private Boolean isFrame;

    /**
     * 外链地址
     */
    @Size(max = 255, message = "外链地址长度不能超过255个字符")
    @Schema(description = "外链地址")
    private String href;

    /**
     * 是否缓存（false不缓存 true缓存）
     */
    @Schema(description = "是否缓存")
    private Boolean keepAlive;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型", required = true)
    private SysMenuTypeEnum menuType;

    /**
     * 菜单是否在菜单中隐藏（false不隐藏 true隐藏）
     */
    @Schema(description = "菜单是否在菜单中隐藏")
    private Boolean hideInMenu;

    /**
     * 菜单状态（0停用 1正常）
     */
    @Schema(description = "菜单状态")
    private StatusEnum status;

    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
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
    @Size(max = 50, message = "高亮路由名称长度不能超过50个字符")
    @Schema(description = "高亮路由名称")
    private String activeMenu;

    /**
     * 菜单在标签页中的顺序
     */
    @Schema(description = "菜单在标签页中的顺序")
    private Integer fixedIndexInTab;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Schema(description = "备注")
    private String remark;

    /**
     * 按钮列表
     */
    @Schema(description = "按钮列表")
    private List<SysBtnDTO> buttons;
}
