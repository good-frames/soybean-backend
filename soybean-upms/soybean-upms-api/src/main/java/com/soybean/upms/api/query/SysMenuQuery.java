
package com.soybean.upms.api.query;

import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单查询对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Schema(description = "菜单查询参数")
@Data
public class SysMenuQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String routeName;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Schema(description = "菜单状态：0正常 1停用")
    private StatusEnum status;

    /**
     * 菜单类型（M目录 C菜单）
     */
    @Schema(description = "菜单类型：M目录 C菜单 F按钮")
    private SysMenuTypeEnum menuType;
}
