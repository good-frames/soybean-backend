
package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.RoleMenuBtnBindDTO;
import com.soybean.upms.api.dto.SysRoleDTO;
import com.soybean.upms.api.dto.UserRoleBindDTO;
import com.soybean.upms.api.enums.SysDelFlagEnum;
import com.soybean.upms.api.enums.SysMenuTypeEnum;
import com.soybean.upms.api.enums.SysRoleStatusEnum;
import com.soybean.upms.api.po.SysRole;
import com.soybean.upms.api.po.SysRoleBtn;
import com.soybean.upms.api.po.SysRoleMenu;
import com.soybean.upms.api.po.SysUserRole;
import com.soybean.upms.api.enums.SysMenuMultiTabEnum;
import com.soybean.upms.api.po.SysMenu;
import com.soybean.upms.api.query.SysRoleQuery;
import com.soybean.upms.api.vo.MenuTreeVO;
import com.soybean.upms.api.vo.RoleMenuBtnVO;
import com.soybean.upms.api.vo.SysRoleVO;
import com.soybean.upms.mapper.SysRoleMapper;
import com.soybean.upms.mapper.SysRoleMenuMapper;
import com.soybean.upms.mapper.SysRoleBtnMapper;
import com.soybean.upms.mapper.SysUserRoleMapper;
import com.soybean.upms.mapper.SysMenuMapper;
import com.soybean.upms.service.ISysRoleService;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final SysRoleBtnMapper roleBtnMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysMenuMapper menuMapper;

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
    public List<SysRoleVO> selectRolesByUserId(String userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        return BeanUtil.copyToList(roleList, SysRoleVO.class);
    }

    /**
     * 根据用户ID获取角色权限字符串列表
     *
     * @param userId 用户ID
     * @return 角色权限字符串列表
     */
    @Override
    public List<String> selectRoleKeysByUserId(String userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        return roleList.stream().map(SysRole::getRoleKey).toList();
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
    public List<Long> selectRoleListByUserId(String userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        if (CollUtil.isEmpty(roleList)) {
            return CollUtil.newArrayList();
        }
        return roleList.stream().map(SysRole::getId).collect(Collectors.toList());
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
        // 检查角色是否存在
        if (!checkRoleExists(roleId)) {
            throw new RuntimeException("角色不存在");
        }

        LambdaUpdateWrapper<SysRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRole::getId, roleId).set(SysRole::getStatus, status.getValue());
        return update(wrapper);
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
        // 检查角色是否全部存在
        if (!checkRolesExists(roleIds)) {
            throw new RuntimeException("部分角色不存在");
        }

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
        Long roleId = role.getId() == null ? -1L : role.getId();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName, role.getRoleName())
                .eq(SysRole::getDelFlag, SysDelFlagEnum.EXIST);
        SysRole info = getOne(wrapper);
        if (info != null && info.getId().longValue() != roleId.longValue()) {
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
        Long roleId = role.getId() == null ? -1L : role.getId();
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleKey, role.getRoleKey())
                .eq(SysRole::getDelFlag, SysDelFlagEnum.EXIST);
        SysRole info = getOne(wrapper);
        if (info != null && info.getId().longValue() != roleId.longValue()) {
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
        if (role.getId() != null && role.getId().longValue() == 1L) {
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
    public Set<String> selectRolePermissionByUserId(String userId) {
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
     * 校验角色是否存在
     *
     * @param roleId 角色ID
     * @return 是否存在
     */
    @Override
    public boolean checkRoleExists(Long roleId) {
        return getById(roleId) != null;
    }

    /**
     * 校验多个角色是否都存在
     *
     * @param roleIds 角色ID列表
     * @return 是否全部存在
     */
    @Override
    public boolean checkRolesExists(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }

        for (Long roleId : roleIds) {
            if (!checkRoleExists(roleId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据用户ID获取角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Override
    public List<Long> getRoleIdsByUserId(String userId) {
        // 查询用户角色关联
        List<SysUserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));

        if (CollUtil.isEmpty(userRoles)) {
            return Collections.emptyList();
        }

        // 提取角色ID
        return userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
    }

    /**
     * 判断是否是管理员
     *
     * @param role 角色
     * @return 结果
     */
    private static boolean isAdmin(SysRole role) {
        return (role != null && role.getId() != null && role.getId().longValue() == 1L);
    }

    @Override
    public RoleMenuBtnVO getRoleMenuBtn(Long roleId) {
        // 创建返回对象
        RoleMenuBtnVO result = new RoleMenuBtnVO();
        // 初始化空列表，确保返回不为null
        result.setMenuIds(new ArrayList<>());
        result.setBtnIds(new ArrayList<>());
        
        // 获取角色绑定的菜单
        List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(
            new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId)
        );
        
        if (CollUtil.isNotEmpty(roleMenus)) {
            // 获取菜单ID列表
            // 获取所有菜单ID
            List<Long> allMenuIds = roleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
                
            // 查询菜单信息，获取所有类型的菜单（包括目录和菜单）
            List<SysMenu> menus = menuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                    .in(SysMenu::getId, allMenuIds)
                    .in(SysMenu::getMenuType, SysMenuTypeEnum.DIRECTORY, SysMenuTypeEnum.MENU)
            );
            
            // 获取菜单ID列表
            List<Long> menuIds = menus.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toList());
            
            result.setMenuIds(menuIds);
        }
        
        // 获取角色绑定的按钮
        List<SysRoleBtn> roleBtns = roleBtnMapper.selectList(
            new LambdaQueryWrapper<SysRoleBtn>().eq(SysRoleBtn::getRoleId, roleId)
        );
        
        if (CollUtil.isNotEmpty(roleBtns)) {
            // 获取按钮ID列表
            List<Long> btnIds = roleBtns.stream()
                .map(SysRoleBtn::getBtnId)
                .collect(Collectors.toList());
            
            result.setBtnIds(btnIds);
        }
        
        return result;
    }

    @Override
    public boolean bindRoleMenuBtn(Long roleId, RoleMenuBtnBindDTO bindDTO) {
        // 校验角色是否存在
        checkRoleExists(roleId);
        
        // 删除原有的角色菜单关联
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
            .eq(SysRoleMenu::getRoleId, roleId));
        
        // 删除原有的角色按钮关联
        roleBtnMapper.delete(new LambdaQueryWrapper<SysRoleBtn>()
            .eq(SysRoleBtn::getRoleId, roleId));
        
        // 保存新的角色菜单关联
        if (CollUtil.isNotEmpty(bindDTO.getMenuIds())) {
            List<SysRoleMenu> roleMenus = bindDTO.getMenuIds().stream()
                .map(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                })
                .collect(Collectors.toList());
            
            for (SysRoleMenu roleMenu : roleMenus) {
                roleMenuMapper.insert(roleMenu);
            }
        }
        
        // 保存新的角色按钮关联
        if (CollUtil.isNotEmpty(bindDTO.getBtnIds())) {
            List<SysRoleBtn> roleBtns = bindDTO.getBtnIds().stream()
                .map(btnId -> {
                    SysRoleBtn roleBtn = new SysRoleBtn();
                    roleBtn.setRoleId(roleId);
                    roleBtn.setBtnId(btnId);
                    return roleBtn;
                })
                .collect(Collectors.toList());
            
            for (SysRoleBtn roleBtn : roleBtns) {
                roleBtnMapper.insert(roleBtn);
            }
        }
        
        return true;
    }

    @Override
    public boolean bindUserRole(UserRoleBindDTO bindDTO) {
        // 删除用户原有的角色关联
        userRoleMapper.deleteUserRoleByUserId(bindDTO.getUserId());

        // 添加新的角色关联
        if (CollUtil.isNotEmpty(bindDTO.getRoleIds())) {
            List<SysUserRole> userRoles = bindDTO.getRoleIds().stream()
                .map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(bindDTO.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList());

            return userRoleMapper.batchUserRole(userRoles) > 0;
        }

        return true;
    }
}
