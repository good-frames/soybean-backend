
package com.soybean.upms.api.dto;

import com.soybean.common.core.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "角色数据传输对象")
public class SysRoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    @Schema(description = "角色名称", required = true)
    private String roleName;

    /**
     * 角色权限字符串
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(max = 100, message = "权限字符长度不能超过100个字符")
    @Schema(description = "角色权限字符串", required = true)
    private String roleKey;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer roleSort;

    /**
     * 角色状态（0停用 1正常）
     */
    @Schema(description = "角色状态")
    private StatusEnum status;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Schema(description = "备注")
    private String remark;

    /**
     * 角色的默认首页路由名称
     */
    @Size(max = 100, message = "首页路由名称长度不能超过100个字符")
    @Schema(description = "角色的默认首页路由名称")
    private String home;
}
