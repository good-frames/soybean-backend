package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统文件存储配置视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "系统文件存储配置视图对象")
public class SysStorageConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 存储类型1、阿里OSS；2、七牛云；3、腾讯云
     */
    @Schema(description = "存储类型")
    private String type;

    /**
     * access_key
     */
    @Schema(description = "access_key")
    private String accessKey;

    /**
     * access_secret
     */
    @Schema(description = "access_secret")
    private String accessSecret;

    /**
     * 地域节点
     */
    @Schema(description = "地域节点")
    private String endpoint;

    /**
     * 域名
     */
    @Schema(description = "域名")
    private String bucket;

    /**
     * 指定文件夹
     */
    @Schema(description = "指定文件夹")
    private String dir;

    /**
     * 状态：0.禁用；1.正常
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
