package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysStorageConfigDTO;
import com.soybean.upms.api.po.SysStorageConfig;
import com.soybean.upms.api.query.SysStorageConfigQuery;
import com.soybean.upms.api.vo.SysStorageConfigVO;
import com.soybean.upms.mapper.SysStorageConfigMapper;
import com.soybean.upms.service.ISysStorageConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统文件存储配置 服务实现类
 *
 * @author soybean
 * @since 2024-07-07
 */
@Service
@RequiredArgsConstructor
public class SysStorageConfigServiceImpl extends ServiceImpl<SysStorageConfigMapper, SysStorageConfig> implements ISysStorageConfigService {

    /**
     * 根据条件分页查询存储配置数据
     *
     * @param query 查询条件
     * @return 分页存储配置信息
     */
    @Override
    public PageDTO<SysStorageConfigVO> getStorageConfigPage(SysStorageConfigQuery query) {
        Page<SysStorageConfig> page = query.toMpPageDefaultSortByCreateTimeDesc(); // 降序排序

        LambdaQueryWrapper<SysStorageConfig> wrapper = Wrappers.<SysStorageConfig>lambdaQuery();
        wrapper.eq(query.getType() != null, SysStorageConfig::getType, query.getType())
                .eq(query.getStatus() != null, SysStorageConfig::getStatus, query.getStatus());

        baseMapper.selectPage(page, wrapper);

        // 使用PageDTO.of方法，直接转换
        return PageDTO.of(page, SysStorageConfigVO.class);
    }

    /**
     * 新增保存存储配置信息
     *
     * @param configDTO 存储配置信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertStorageConfig(SysStorageConfigDTO configDTO) {
        // 新增存储配置信息
        SysStorageConfig config = BeanUtil.copyProperties(configDTO, SysStorageConfig.class);
        // 如果状态为空，设置默认值
        if (config.getStatus() == null) {
            config.setStatus("1");
        }
        int result = baseMapper.insert(config);

        return result > 0;
    }

    /**
     * 修改保存存储配置信息
     *
     * @param configDTO 存储配置信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStorageConfig(SysStorageConfigDTO configDTO) {
        // 修改存储配置信息
        SysStorageConfig config = BeanUtil.copyProperties(configDTO, SysStorageConfig.class);
        int result = baseMapper.updateById(config);

        return result > 0;
    }

    /**
     * 批量删除存储配置信息
     *
     * @param ids 需要删除的存储配置ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStorageConfigByIds(java.util.List<Long> ids) {
        // 删除存储配置
        return removeByIds(ids);
    }
}
