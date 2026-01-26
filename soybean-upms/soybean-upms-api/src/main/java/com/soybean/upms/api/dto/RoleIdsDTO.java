package com.soybean.upms.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * 角色ID列表DTO
 *
 * @author soybean
 * @since 2024-07-07
 */
@Schema(description = "角色ID列表数据传输对象")
@Data
public class RoleIdsDTO {

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    @Schema(description = "角色ID列表", required = true)
    private List<Long> roleIds;
}
