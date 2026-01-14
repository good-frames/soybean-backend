package com.soybean.upms.api.vo;

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
public class SysStorageConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 存储类型1、阿里OSS；2、七牛云；3、腾讯云
     */
    private String type;

    /**
     * access_key
     */
    private String accessKey;

    /**
     * access_secret
     */
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
     * 状态：0.禁用；1.正常
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
