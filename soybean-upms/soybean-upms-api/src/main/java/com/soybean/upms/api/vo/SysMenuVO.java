
package com.soybean.upms.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.upms.api.enums.SysMenuStatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import com.soybean.upms.api.enums.SysMenuVisibleEnum;
import com.soybean.upms.api.enums.SysMenuFrameEnum;
import com.soybean.upms.api.enums.SysMenuCacheEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class SysMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
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
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子菜单
     */
    private List<SysMenuVO> children;
}
