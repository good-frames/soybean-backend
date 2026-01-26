package com.soybean.upms.api.dto;

import com.soybean.upms.api.enums.SysUserGenderEnum;
import com.soybean.common.core.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员DTO
 *
 * @author soybean
 */
@Data
@Schema(description = "管理员数据传输对象")
public class SysUserDTO implements Serializable {

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
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 状态：0->禁用；1->启用
     */
    @Schema(description = "状态")
    private StatusEnum status;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private SysUserGenderEnum gender;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 登录IP（用于登录时记录）
     */
    @Schema(description = "登录IP")
    private String loginIp;
}
