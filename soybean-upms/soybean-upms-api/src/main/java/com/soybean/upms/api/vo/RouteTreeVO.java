package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树VO（前端路由格式）
 *
 * @author soybean
 */
@Data
@Schema(description = "菜单树视图对象（前端路由格式）")
public class RouteTreeVO implements Serializable {

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
    private String name;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单列表")
    private List<RouteTreeVO> children;

    /**
     * 按钮列表
     */
    @Schema(description = "按钮列表")
    private List<SysBtnVO> buttons;

    /**
     * 元数据
     */
    @Schema(description = "菜单元数据")
    private MenuMetaVO meta;
    
    /**
     * 菜单元数据VO
     *
     * @author soybean
     */
    @Data
    @Schema(description = "菜单元数据视图对象")
    public static class MenuMetaVO {

        /**
         * 国际化键
         */
        @Schema(description = "国际化键")
        private String i18nKey;

        /**
         * 图标
         */
        @Schema(description = "图标")
        private String icon;

        /**
         * 排序
         */
        @Schema(description = "排序")
        private Integer order;

        /**
         * 外链地址
         */
        @Schema(description = "外链地址")
        private String href;

        /**
         * 是否为静态菜单（0否 1是）
         */
        @Schema(description = "是否为静态菜单")
        private Boolean constant;

        /**
         * 是否在菜单中隐藏
         */
        @Schema(description = "是否在菜单中隐藏")
        private Boolean hideInMenu;

        /**
         * 是否缓存
         */
        @Schema(description = "是否缓存")
        private Boolean keepAlive;

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
    }
}
