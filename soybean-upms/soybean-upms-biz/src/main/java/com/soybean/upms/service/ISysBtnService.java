package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.api.po.SysBtn;

import java.util.List;

/**
 * 按钮权限Service接口
 *
 * @author soybean
 * @since 2026-01-04
 */
public interface ISysBtnService extends IService<SysBtn> {

    /**
     * 查询按钮信息
     *
     * @param id 按钮ID
     * @return 按钮信息
     */
    SysBtnDTO selectBtnById(Long id);

    /**
     * 查询按钮列表
     *
     * @param btnDTO 按钮信息
     * @return 按钮集合
     */
    List<SysBtnDTO> selectBtnList(SysBtnDTO btnDTO);

    /**
     * 根据菜单ID查询按钮列表
     *
     * @param menuId 菜单ID
     * @return 按钮集合
     */
    List<SysBtnDTO> selectBtnListByMenuId(Long menuId);

    /**
     * 新增保存按钮信息
     *
     * @param btnDTO 按钮信息
     * @return 结果
     */
    boolean insertBtn(SysBtnDTO btnDTO);

    /**
     * 批量新增按钮
     *
     * @param btnList 按钮列表
     * @return 结果
     */
    boolean insertBatch(List<SysBtn> btnList);

    /**
     * 修改保存按钮信息
     *
     * @param btnDTO 按钮信息
     * @return 结果
     */
    boolean updateBtn(SysBtnDTO btnDTO);

    /**
     * 批量删除按钮信息
     *
     * @param ids 需要删除的按钮ID
     * @return 结果
     */
    boolean deleteBtnByIds(List<Long> ids);

    /**
     * 删除按钮信息
     *
     * @param id 按钮ID
     * @return 结果
     */
    boolean deleteBtnById(Long id);
}
