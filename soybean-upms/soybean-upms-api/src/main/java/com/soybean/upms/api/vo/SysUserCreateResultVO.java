package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增用户结果VO
 *
 * @author soybean
 */
@Data
@Schema(description = "新增用户结果视图对象")
public class SysUserCreateResultVO implements Serializable {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 生成的随机密码（仅在新增时返回）
     */
    @Schema(description = "生成的随机密码")
    private String generatedPassword;
}
