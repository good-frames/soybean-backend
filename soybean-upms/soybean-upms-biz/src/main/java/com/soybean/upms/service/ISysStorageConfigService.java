package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysStorageConfigDTO;
import com.soybean.upms.api.po.SysStorageConfig;
import com.soybean.upms.api.query.SysStorageConfigQuery;
import com.soybean.upms.api.vo.SysStorageConfigVO;

/**
 * 系统文件存储配置 服务类
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysStorageConfigService extends IService<SysStorageConfig> {

    /**
     * 根据条件分页查询存储配置数据
     *
     * @param query 查询条件
     * @return 分页存储配置信息
     */
    PageDTO<SysStorageConfigVO> getStorageConfigPage(SysStorageConfigQuery query);

    /**
     * 新增保存存储配置信息
     *
     * @param configDTO 存储配置信息
     * @return 结果
     */
    boolean insertStorageConfig(SysStorageConfigDTO configDTO);

    /**
     * 修改保存存储配置信息
     *
     * @param configDTO 存储配置信息
     * @return 结果
     */
    boolean updateStorageConfig(SysStorageConfigDTO configDTO);

    /**
     * 批量删除存储配置信息
     *
     * @param ids 需要删除的存储配置ID
     * @return 结果
     */
    boolean deleteStorageConfigByIds(java.util.List<Long> ids);
}
