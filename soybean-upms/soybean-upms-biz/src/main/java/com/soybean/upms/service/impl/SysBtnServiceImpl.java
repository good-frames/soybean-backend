package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.api.po.SysBtn;
import com.soybean.upms.mapper.SysBtnMapper;
import com.soybean.upms.service.ISysBtnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 按钮权限Service业务层处理
 *
 * @author soybean
 * @since 2026-01-04
 */
@Service
public class SysBtnServiceImpl extends ServiceImpl<SysBtnMapper, SysBtn> implements ISysBtnService {

    /**
     * 查询按钮信息
     *
     * @param id 按钮ID
     * @return 按钮信息
     */
    @Override
    public SysBtnDTO selectBtnById(Long id) {
        SysBtn btn = baseMapper.selectById(id);
        if (btn == null) {
            return null;
        }
        return BeanUtil.copyProperties(btn, SysBtnDTO.class);
    }

    /**
     * 查询按钮列表
     *
     * @param btnDTO 按钮信息
     * @return 按钮集合
     */
    @Override
    public List<SysBtnDTO> selectBtnList(SysBtnDTO btnDTO) {
        LambdaQueryWrapper<SysBtn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(btnDTO.getMenuId() != null, SysBtn::getMenuId, btnDTO.getMenuId())
                .like(StringUtils.isNotBlank(btnDTO.getBtnCode()), SysBtn::getBtnCode, btnDTO.getBtnCode());

        List<SysBtn> btnList = baseMapper.selectList(wrapper);
        if (CollUtil.isEmpty(btnList)) {
            return CollUtil.newArrayList();
        }

        return btnList.stream()
                .map(btn -> BeanUtil.copyProperties(btn, SysBtnDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据菜单ID查询按钮列表
     *
     * @param menuId 菜单ID
     * @return 按钮集合
     */
    @Override
    public List<SysBtnDTO> selectBtnListByMenuId(Long menuId) {
        LambdaQueryWrapper<SysBtn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBtn::getMenuId, menuId);

        List<SysBtn> btnList = baseMapper.selectList(wrapper);
        if (CollUtil.isEmpty(btnList)) {
            return CollUtil.newArrayList();
        }

        return btnList.stream()
                .map(btn -> BeanUtil.copyProperties(btn, SysBtnDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 新增保存按钮信息
     *
     * @param btnDTO 按钮信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBtn(SysBtnDTO btnDTO) {
        // 新增按钮信息
        SysBtn btn = BeanUtil.copyProperties(btnDTO, SysBtn.class);
        return baseMapper.insert(btn) > 0;
    }
    
    /**
     * 批量新增按钮
     *
     * @param btnList 按钮列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(List<SysBtn> btnList) {
        if (CollUtil.isEmpty(btnList)) {
            return false;
        }
        return this.saveBatch(btnList);
    }

    /**
     * 修改保存按钮信息
     *
     * @param btnDTO 按钮信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBtn(SysBtnDTO btnDTO) {
        SysBtn btn = BeanUtil.copyProperties(btnDTO, SysBtn.class);
        return baseMapper.updateById(btn) > 0;
    }

    /**
     * 批量删除按钮信息
     *
     * @param ids 需要删除的按钮ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBtnByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 删除按钮信息
     *
     * @param id 按钮ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBtnById(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}
