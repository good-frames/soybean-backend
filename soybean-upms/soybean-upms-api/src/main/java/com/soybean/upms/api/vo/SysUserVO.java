package com.soybean.upms.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soybean.upms.api.enums.SysUserGenderEnum;
import com.soybean.common.core.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 管理员VO
 *
 * @author soybean
 */
@Data
@Schema(description = "管理员视图对象")
public class SysUserVO {

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
     * 状态名称：禁用、启用
     */
    @Schema(description = "状态名称")
    private String statusName;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

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
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roles;

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<String> permissions;
}
