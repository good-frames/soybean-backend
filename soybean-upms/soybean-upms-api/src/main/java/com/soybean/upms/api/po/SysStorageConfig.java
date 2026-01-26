package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;
import com.soybean.upms.api.enums.SysStorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统文件存储配置
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@TableName(value = "sys_storage_config")
@Schema(description = "系统文件存储配置")
public class SysStorageConfig implements Serializable {

	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 存储类型
     * AliOss: 阿里云OSS
     * QiNiuCloud: 七牛云
     * QCloudCos: 腾讯云COS
     * Minio: MinIO
	 */
	@Schema(description = "存储类型")
	private SysStorageTypeEnum type;

	/**
	 * access_key
	 */
	@TableField(value = "access_key")
	@Schema(description = "access_key")
	private String accessKey;

	/**
	 * access_secret
	 */
	@TableField(value = "access_secret")
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
     * 状态：0.禁用；1.正常；
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 创建者
     */
    @TableField("create_by")
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    @Schema(description = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
