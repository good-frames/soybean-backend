package com.soybean.upms.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增用户结果VO
 *
 * @author soybean
 */
@Data
public class SysUserCreateResultVO implements Serializable {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 生成的随机密码（仅在新增时返回）
     */
    private String generatedPassword;
}
