package com.soybean.upms.api.vo;

import lombok.Data;

/**
 * 菜单元数据VO
 *
 * @author soybean
 */
@Data
public class MenuMetaVO {

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
}
