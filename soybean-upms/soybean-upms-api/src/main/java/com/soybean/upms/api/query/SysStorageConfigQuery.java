package com.soybean.upms.api.query;

import com.soybean.common.mybatis.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统文件存储配置查询对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysStorageConfigQuery extends PageQuery {

    /**
     * 存储类型1、阿里OSS；2、七牛云；3、腾讯云
     */
    private String type;

    /**
     * 状态：0.正常；1.禁用
     */
    private String status;

    /**
     * 时间范围-开始时间
     */
    private String beginTime;

    /**
     * 时间范围-结束时间
     */
    private String endTime;
}
