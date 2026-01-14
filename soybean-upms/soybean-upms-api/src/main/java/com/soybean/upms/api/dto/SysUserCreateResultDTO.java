package com.soybean.upms.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增用户结果DTO
 *
 * @author soybean
 */
@Data
public class SysUserCreateResultDTO implements Serializable {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 生成的随机密码（仅在新增时返回）
     */
    private String generatedPassword;
}
