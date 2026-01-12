package com.soybean.upms.api.vo;

import com.soybean.upms.api.enums.SysMenuStatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import com.soybean.upms.api.enums.SysMenuHideInMenuEnum;
import com.soybean.upms.api.enums.SysMenuFrameEnum;
import com.soybean.upms.api.enums.SysKeepAliveEnum;
import com.soybean.upms.api.enums.SysIconTypeEnum;
import com.soybean.upms.api.enums.SysMenuMultiTabEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树VO（前端路由格式）
 *
 * @author soybean
 */
@Data
public class RouteTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID
     */
    private Long parentId;

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
    private List<RouteTreeVO> children;

    /**
     * 按钮列表
     */
    private List<SysBtnVO> buttons;

    /**
     * 元数据
     */
    private MenuMetaVO meta;
    
    /**
     * 菜单元数据VO
     *
     * @author soybean
     */
    @Data
    public static class MenuMetaVO {

        /**
         * 国际化键
         */
        private String i18nKey;

        /**
         * 图标
         */
        private String icon;

        /**
         * 排序
         */
        private Integer order;

        /**
         * 外链地址
         */
        private String href;

        /**
         * 是否为静态菜单（0否 1是）
         */
        private Boolean constant;

        /**
         * 是否在菜单中隐藏
         */
        private Boolean hideInMenu;

        /**
         * 是否缓存
         */
        private Boolean keepAlive;

        /**
         * 是否多页签（false否 true是）
         */
        private Boolean multiTab;

        /**
         * 高亮路由名称
         */
        private String activeMenu;

        /**
         * 菜单在标签页中的顺序
         */
        private Integer fixedIndexInTab;
    }
}
