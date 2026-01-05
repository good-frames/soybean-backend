package com.soybean.upms.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 按钮视图对象
 *
 * @author soybean
 * @since 2026-01-05
 */
@Data
public class SysBtnVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 按钮ID
     */
    private Long id;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 按钮标识
     */
    private String btnCode;

    /**
     * 备注
     */
    private String remark;
}