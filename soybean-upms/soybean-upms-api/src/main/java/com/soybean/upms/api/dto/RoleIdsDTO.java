package com.soybean.upms.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * 角色ID列表DTO
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
public class RoleIdsDTO {

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}
