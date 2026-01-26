package com.soybean.upms.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统文件存储配置数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "系统文件存储配置数据传输对象")
public class SysStorageConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 存储类型1、阿里OSS；2、七牛云；3、腾讯云
     */
    @NotNull(message = "存储类型不能为空")
    @Schema(description = "存储类型", required = true)
    private String type;

    /**
     * access_key
     */
    @NotBlank(message = "access_key不能为空")
    @Size(max = 255, message = "access_key长度不能超过255个字符")
    @Schema(description = "access_key", required = true)
    private String accessKey;

    /**
     * access_secret
     */
    @NotBlank(message = "access_secret不能为空")
    @Size(max = 255, message = "access_secret长度不能超过255个字符")
    @Schema(description = "access_secret", required = true)
    private String accessSecret;

    /**
     * 地域节点
     */
    @NotBlank(message = "地域节点不能为空")
    @Size(max = 255, message = "地域节点长度不能超过255个字符")
    @Schema(description = "地域节点", required = true)
    private String endpoint;

    /**
     * 域名
     */
    @NotBlank(message = "域名不能为空")
    @Size(max = 255, message = "域名长度不能超过255个字符")
    @Schema(description = "域名", required = true)
    private String bucket;

    /**
     * 指定文件夹
     */
    @Size(max = 255, message = "指定文件夹长度不能超过255个字符")
    @Schema(description = "指定文件夹")
    private String dir;

    /**
     * 状态：0.禁用；1.正常
     */
    @Schema(description = "状态")
    private String status;
}
