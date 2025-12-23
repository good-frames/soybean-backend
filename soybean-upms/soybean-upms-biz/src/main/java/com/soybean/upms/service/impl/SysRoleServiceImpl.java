
package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysRoleDTO;
import com.soybean.upms.api.enums.SysDelFlagEnum;
import com.soybean.upms.api.enums.SysRoleStatusEnum;
import com.soybean.upms.api.po.SysRole;
import com.soybean.upms.api.po.SysRoleMenu;
import com.soybean.upms.api.query.SysRoleQuery;
import com.soybean.upms.api.vo.SysRoleVO;
import com.soybean.upms.mapper.SysRoleMapper;
import com.soybean.upms.mapper.SysRoleMenuMapper;
import com.soybean.upms.mapper.SysUserRoleMapper;
import com.soybean.upms.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色表 服务实现类
 *
 * @author soybean
 * @since 2024-07-07
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMenuMapper roleMenuMapper;
    private final SysUserRoleMapper userRoleMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param query 查询条件
     * @return 分页角色信息
     */
    @Override
    public PageDTO<SysRoleVO> getRolePage(SysRoleQuery query) {
        Page<SysRole> page = query.toMpPageDefaultSortByCreateTimeDesc(); // 降序排序

        LambdaQueryWrapper<SysRole> wrapper = Wrappers.<SysRole>lambdaQuery();
        wrapper.like(query.getRoleName() != null, SysRole::getRoleName, query.getRoleName())
                .like(query.getRoleKey() != null, SysRole::getRoleKey, query.getRoleKey())
                .eq(query.getStatus() != null, SysRole::getStatus, query.getStatus())
                .between(query.getBeginTime() != null && query.getEndTime() != null,
                        SysRole::getCreateTime, query.getBeginTime(), query.getEndTime());

        baseMapper.selectPage(page, wrapper);

        // 使用PageDTO.of方法，直接转换
        return PageDTO.of(page, SysRoleVO.class);
    }

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRoleVO> selectRolesByUserId(Long userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        return BeanUtil.copyToList(roleList, SysRoleVO.class);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRoleVO> selectRoleAll() {
        List<SysRole> roleList = baseMapper.selectRoleAll();
        return BeanUtil.copyToList(roleList, SysRoleVO.class);
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        if (CollUtil.isEmpty(roleList)) {
            return CollUtil.newArrayList();
        }
        return roleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 新增保存角色信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(SysRoleDTO roleDTO) {
        // 新增角色信息
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        // 如果状态为空，设置默认值
        if (role.getStatus() == null) {
            role.setStatus(SysRoleStatusEnum.NORMAL);
        }
        int result = baseMapper.insert(role);

        // 新增角色菜单关联
        if (result > 0 && CollUtil.isNotEmpty(roleDTO.getMenuIds())) {
            insertRoleMenu(role.getRoleId(), roleDTO.getMenuIds());
        }
        return result > 0;
    }

    /**
     * 修改保存角色信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SysRoleDTO roleDTO) {
        // 修改角色信息
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        int result = baseMapper.updateById(role);

        // 删除角色与菜单关联
        if (result > 0) {
            roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        }

        // 新增角色菜单关联
        if (result > 0 && CollUtil.isNotEmpty(roleDTO.getMenuIds())) {
            insertRoleMenu(role.getRoleId(), roleDTO.getMenuIds());
        }
        return result > 0;
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态
     * @return 结果
     */
    @Override
    public boolean updateRoleStatus(Long roleId, SysRoleStatusEnum status) {
        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getRoleId, roleId).set(SysRole::getStatus, status.getValue());
        return update(wrapper);
    }

    /**
     * 修改数据权限信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    @Override
    public boolean authDataScope(SysRoleDTO roleDTO) {
        // 修改角色信息
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        int result = baseMapper.updateById(role);

        // 删除角色与菜单关联
        if (result > 0) {
            roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        }

        // 新增角色菜单关联
        if (result > 0 && CollUtil.isNotEmpty(roleDTO.getMenuIds())) {
            insertRoleMenu(role.getRoleId(), roleDTO.getMenuIds());
        }
        return result > 0;
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleByIds(List<Long> roleIds) {
        // 删除角色与菜单关联
        for (Long roleId : roleIds) {
            roleMenuMapper.deleteRoleMenuByRoleId(roleId);
            userRoleMapper.deleteUserRoleByRoleId(roleId);
        }

        // 删除角色
        return removeByIds(roleIds);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRoleDTO role) {
        Long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, role.getRoleName())
                .eq(SysRole::getDelFlag, SysDelFlagEnum.EXIST);
        SysRole info = getOne(wrapper);
        if (info != null && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRoleDTO role) {
        Long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleKey, role.getRoleKey())
                .eq(SysRole::getDelFlag, SysDelFlagEnum.EXIST);
        SysRole info = getOne(wrapper);
        if (info != null && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRoleDTO role) {
        if (role.getRoleId() != null && role.getRoleId().longValue() == 1L) {
            throw new RuntimeException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!SysRoleServiceImpl.isAdmin(getById(roleId))) {
            // TODO: 实现数据权限校验
        }
    }

    /**
     * 根据用户ID获取角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = baseMapper.selectRoleListByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (perm != null) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 新增角色菜单信息
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    public void insertRoleMenu(Long roleId, List<Long> menuIds) {
        List<SysRoleMenu> list = CollUtil.newArrayList();
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (CollUtil.isNotEmpty(list)) {
            roleMenuMapper.batchRoleMenu(list);
        }
    }

    /**
     * 判断是否是管理员
     *
     * @param role 角色
     * @return 结果
     */
    private static boolean isAdmin(SysRole role) {
        return (role != null && role.getRoleId() != null && role.getRoleId().longValue() == 1L);
    }
}
