
package com.soybean.upms.api.query;

import com.soybean.upms.api.enums.SysMenuStatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单查询对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class SysMenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单状态（0正常 1停用）
     */
    private SysMenuStatusEnum status;

    /**
     * 菜单类型（M目录 C菜单）
     */
    private SysMenuTypeEnum type;
}
