package com.soybean.upms.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单树VO（前端路由格式）
 *
 * @author soybean
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouteTreeVO extends SysMenuVO {

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
         * 标题
         */
        private String title;

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
    }
}
