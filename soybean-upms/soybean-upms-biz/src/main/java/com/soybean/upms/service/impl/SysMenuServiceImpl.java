
package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.po.SysMenu;
import com.soybean.upms.api.po.SysRoleMenu;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.mapper.SysMenuMapper;
import com.soybean.upms.mapper.SysRoleMenuMapper;
import com.soybean.upms.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单权限表 服务实现类
 *
 * @author soybean
 * @since 2024-07-07
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuVO> selectMenuList(String userId) {
        return selectMenuList(new SysMenuQuery(), userId);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menuQuery 菜单查询条件
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuVO> selectMenuList(SysMenuQuery menuQuery, String userId) {
        List<SysMenu> menuList;

        if (userId != null) {
            menuList = baseMapper.selectMenuListByUserId(userId);
        } else {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            if (menuQuery != null) {
                wrapper.like(menuQuery.getMenuName() != null, SysMenu::getMenuName, menuQuery.getMenuName())
                        .eq(menuQuery.getStatus() != null, SysMenu::getStatus, menuQuery.getStatus())
                        .eq(menuQuery.getMenuType() != null, SysMenu::getMenuType, menuQuery.getMenuType());
            }
            wrapper.orderByAsc(SysMenu::getParentId)
                    .orderByAsc(SysMenu::getOrderNum);
            menuList = list(wrapper);
        }

        return BeanUtil.copyToList(menuList, SysMenuVO.class);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<String> selectPermissionsByUserId(String userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(-1L);
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> perms = roleMenuMapper.selectList(wrapper);

        List<Long> menuIds = new ArrayList<>();
        for (SysRoleMenu perm : perms) {
            if (perm != null && perm.getMenuId() != null) {
                menuIds.add(perm.getMenuId());
            }
        }
        return menuIds;
    }

    /**
     * 根据角色ID数组查询菜单列表（扁平化）
     *
     * @param roleIds 角色ID数组
     * @return 菜单列表
     */
    @Override
    public List<SysMenuVO> selectMenuFlatListByRoleIds(Long[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return CollUtil.newArrayList();
        }

        // 获取所有角色关联的菜单ID列表（去重）
        Set<Long> menuIdSet = new HashSet<>();
        for (Long roleId : roleIds) {
            List<Long> menuIds = selectMenuListByRoleId(roleId);
            if (CollUtil.isNotEmpty(menuIds)) {
                menuIdSet.addAll(menuIds);
            }
        }

        if (CollUtil.isEmpty(menuIdSet)) {
            return CollUtil.newArrayList();
        }

        // 查询菜单详情
        List<SysMenu> menuList = listByIds(menuIdSet);
        if (CollUtil.isEmpty(menuList)) {
            return CollUtil.newArrayList();
        }

        // 转换为VO并按排序
        List<SysMenuVO> menuVOList = BeanUtil.copyToList(menuList, SysMenuVO.class);
        menuVOList.sort((a, b) -> {
            // 先按父ID排序
            int parentCompare = a.getParentId().compareTo(b.getParentId());
            if (parentCompare != 0) {
                return parentCompare;
            }
            // 再按排序号排序
            return a.getOrderNum().compareTo(b.getOrderNum());
        });

        return menuVOList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<SysMenuVO> buildMenuTree(List<SysMenuVO> menus) {
        List<SysMenuVO> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(SysMenuVO::getMenuId).collect(Collectors.toList());

        for (SysMenuVO menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }

        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenuVO> buildMenuTreeSelect(List<SysMenuVO> menus) {
        List<SysMenuVO> menuTrees = buildMenuTree(menus);
        return menuTrees;
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuVO selectMenuById(Long menuId) {
        SysMenu menu = getById(menuId);
        if (menu == null) {
            return null;
        }
        return BeanUtil.copyProperties(menu, SysMenuVO.class);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = Math.toIntExact(count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId)));
        return result > 0;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = roleMenuMapper.countMenuByRoleId(menuId);
        return result > 0;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean insertMenu(SysMenuDTO menu) {
        SysMenu info = BeanUtil.copyProperties(menu, SysMenu.class);
        return save(info);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean updateMenu(SysMenuDTO menu) {
        SysMenu info = BeanUtil.copyProperties(menu, SysMenu.class);
        return updateById(info);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean deleteMenuById(Long menuId) {
        return removeById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenuDTO menu) {
        Long menuId = menu.getMenuId() == null ? -1L : menu.getMenuId();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getMenuName, menu.getMenuName())
                .eq(SysMenu::getParentId, menu.getParentId());
        SysMenu info = getOne(wrapper);
        if (info != null && info.getMenuId().longValue() != menuId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenuVO> list, SysMenuVO t) {
        // 得到子节点列表
        List<SysMenuVO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuVO tChild : childList) {
            // 判断是否有子节点
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuVO> getChildList(List<SysMenuVO> list, SysMenuVO t) {
        List<SysMenuVO> tlist = new ArrayList<>();
        for (SysMenuVO n : list) {
            if (n.getParentId() != null && n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuVO> list, SysMenuVO t) {
        return !getChildList(list, t).isEmpty();
    }
}
