
package com.soybean.upms.api.dto;

import com.soybean.upms.api.enums.SysMenuTypeEnum;
import com.soybean.upms.api.enums.SysMenuStatusEnum;
import com.soybean.upms.api.enums.SysMenuVisibleEnum;
import com.soybean.upms.api.enums.SysMenuFrameEnum;
import com.soybean.upms.api.enums.SysMenuCacheEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class SysMenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Size(max = 200, message = "路由地址长度不能超过200个字符")
    private String path;

    /**
     * 组件路径
     */
    @Size(max = 255, message = "组件路径长度不能超过255个字符")
    private String component;

    /**
     * 路由参数
     */
    @Size(max = 255, message = "路由参数长度不能超过255个字符")
    private String query;

    /**
     * 是否为外链（0不是外链 1是外链）
     */
    private SysMenuFrameEnum isFrame;

    /**
     * 是否缓存（0不缓存 1缓存）
     */
    private SysMenuCacheEnum isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    private SysMenuTypeEnum menuType;

    /**
     * 菜单状态（0隐藏 1显示）
     */
    private SysMenuVisibleEnum visible;

    /**
     * 菜单状态（0停用 1正常）
     */
    private SysMenuStatusEnum status;

    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    private String icon;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    /**
     * 子菜单
     */
    private List<SysMenuDTO> children;
}
