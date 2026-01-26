package com.soybean.upms.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 密码修改DTO
 *
 * @author soybean
 */
@Data
@Schema(description = "密码修改数据传输对象")
public class PasswordUpdateDTO implements Serializable {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String id;

    /**
     * 原密码
     */
    @Schema(description = "原密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPassword;
}
