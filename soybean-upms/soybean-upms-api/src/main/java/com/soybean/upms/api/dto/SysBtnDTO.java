package com.soybean.upms.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 按钮数据传输对象
 *
 * @author soybean
 * @since 2026-01-04
 */
@Data
public class SysBtnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 按钮ID
     */
    private Long id;

    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空")
    private Long menuId;

    /**
     * 按钮标识
     */
    @NotBlank(message = "按钮标识不能为空")
    @Size(max = 100, message = "按钮标识长度不能超过100个字符")
    private String btnCode;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}
