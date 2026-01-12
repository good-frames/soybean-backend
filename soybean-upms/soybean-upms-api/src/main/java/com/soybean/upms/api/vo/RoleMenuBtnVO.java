package com.soybean.upms.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单按钮VO
 *
 * @author soybean
 * @since 2024-07-12
 */
@Data
public class RoleMenuBtnVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    /**
     * 按钮ID列表
     */
    private List<Long> btnIds;
}
