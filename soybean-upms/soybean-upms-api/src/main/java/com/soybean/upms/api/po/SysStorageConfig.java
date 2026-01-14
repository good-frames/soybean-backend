package com.soybean.upms.api.po;

import com.baomidou.mybatisplus.annotation.*;

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
public class SysStorageConfig implements Serializable {

	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 存储类型
     * Aliyun: 阿里OSS
     * Qiniu:七牛云
     * QQ: 腾讯云
	 */
	private String type;

	/**
	 * access_key
	 */
	@TableField(value = "access_key")
	private String accessKey;

	/**
	 * access_secret
	 */
	@TableField(value = "access_secret")
	private String accessSecret;

	/**
	 * 地域节点
	 */
	private String endpoint;

	/**
	 * 域名
	 */
	private String bucket;

	/**
	 * 指定文件夹
	 */
	private String dir;

    /**
     * 状态：0.禁用；1.正常；
     */
    private String status;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
