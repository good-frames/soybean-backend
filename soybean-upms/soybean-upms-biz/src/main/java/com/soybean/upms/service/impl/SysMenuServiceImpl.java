package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.core.exception.BusinessException;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.enums.SysMenuConstantEnum;
import com.soybean.upms.api.enums.SysMenuMultiTabEnum;
import com.soybean.upms.api.enums.SysMenuStatusEnum;
import com.soybean.upms.api.po.SysBtn;
import com.soybean.upms.api.po.SysMenu;
import com.soybean.upms.api.po.SysRoleMenu;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.api.query.SysMenuTreeQuery;
import com.soybean.upms.api.vo.MenuTreeVO;
import com.soybean.upms.api.vo.RouteTreeVO;
import com.soybean.upms.api.vo.SysBtnVO;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.mapper.SysBtnMapper;
import com.soybean.upms.mapper.SysMenuMapper;
import com.soybean.upms.mapper.SysRoleMenuMapper;
import com.soybean.upms.service.ISysBtnService;
import com.soybean.upms.service.ISysMenuService;
import com.soybean.upms.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单权限Service业务层处理
 *
 * @author soybean
 * @since 2024-07-07
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final ISysRoleService sysRoleService;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysBtnMapper sysBtnMapper;
    private final ISysBtnService sysBtnService;

    @Override
    public List<SysMenu> getMenusByUserId(String userId) {
        // 查询用户角色
        List<Long> roleIds = sysRoleService.getRoleIdsByUserId(userId);

        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        // 查询角色菜单
        List<Long> menuIds = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .in(SysRoleMenu::getRoleId, roleIds))
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        // 查询菜单信息
        return list(new LambdaQueryWrapper<SysMenu>()
                .in(SysMenu::getId, menuIds)
                .eq(SysMenu::getStatus, SysMenuStatusEnum.NORMAL)
                .orderByAsc(SysMenu::getOrder));
    }

    @Override
    public List<String> getPermissionsByUserId(String userId) {
        List<SysMenu> menus = getMenusByUserId(userId);

        // 提取权限标识
        List<String> permissions = menus.stream()
                .map(SysMenu::getPerms)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());

        // 查询按钮权限
        List<Long> menuIds = menus.stream()
                .map(SysMenu::getId)
                .toList();

        if (CollUtil.isNotEmpty(menuIds)) {
            List<SysBtn> btnList = sysBtnMapper.selectList(new LambdaQueryWrapper<SysBtn>()
                    .in(SysBtn::getMenuId, menuIds));

            List<String> btnPermissions = btnList.stream()
                    .map(SysBtn::getBtnCode)
                    .toList();

            permissions.addAll(btnPermissions);
        }

        return permissions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMenuWithButtons(SysMenuDTO menuDTO) {
        // 检查菜单名称是否已存在
        long count = count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getRouteName, menuDTO.getRouteName()));
        if (count > 0) {
            throw new BusinessException("菜单名称已存在，请使用其他名称");
        }
        
        // 转换DTO为PO，但排除constant和multiTab字段
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(menuDTO, menu, "constant", "multiTab");
        // 手动处理Boolean到枚举的转换
        if (menuDTO.getConstant() != null) {
            menu.setConstant(menuDTO.getConstant() ? SysMenuConstantEnum.YES : SysMenuConstantEnum.NO);
        }
        if (menuDTO.getMultiTab() != null) {
            menu.setMultiTab(menuDTO.getMultiTab() ? SysMenuMultiTabEnum.YES : SysMenuMultiTabEnum.NO);
        }
        boolean result = save(menu);

        // 保存按钮信息
        if (result && CollUtil.isNotEmpty(menuDTO.getButtons())) {
            List<SysBtn> btnList = menuDTO.getButtons().stream()
                .filter(btnDTO -> StrUtil.isNotBlank(btnDTO.getBtnCode())) // 过滤掉btnCode为空的按钮
                .map(btnDTO -> {
                    SysBtn btn = BeanUtil.toBean(btnDTO, SysBtn.class);
                    btn.setMenuId(menu.getId());
                    return btn;
                })
                .collect(Collectors.toList());
            sysBtnService.saveBatch(btnList);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenuWithButtons(SysMenuDTO menuDTO) {
        // 检查菜单名称是否已存在（排除自身）
        long count = count(new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getRouteName, menuDTO.getRouteName())
            .ne(SysMenu::getId, menuDTO.getId()));
        if (count > 0) {
            throw new BusinessException("菜单名称已存在，请使用其他名称");
        }
        
        // 更新菜单信息，但排除constant和multiTab字段
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(menuDTO, menu, "constant", "multiTab");
        // 手动处理Boolean到枚举的转换
        if (menuDTO.getConstant() != null) {
            menu.setConstant(menuDTO.getConstant() ? SysMenuConstantEnum.YES : SysMenuConstantEnum.NO);
        }
        if (menuDTO.getMultiTab() != null) {
            menu.setMultiTab(menuDTO.getMultiTab() ? SysMenuMultiTabEnum.YES : SysMenuMultiTabEnum.NO);
        }
        boolean result = updateById(menu);

        if (result) {
            // 获取当前菜单的所有按钮
            List<SysBtn> currentBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                .eq(SysBtn::getMenuId, menu.getId()));
            Map<Long, SysBtn> currentBtnMap = currentBtnList.stream()
                .collect(Collectors.toMap(SysBtn::getId, btn -> btn));
            
            // 处理传入的按钮信息
            List<SysBtn> updateBtnList = new ArrayList<>();
            List<SysBtn> insertBtnList = new ArrayList<>();
            Set<Long> updateBtnIds = new HashSet<>();
            
            if (CollUtil.isNotEmpty(menuDTO.getButtons())) {
                for (SysBtnDTO btnDTO : menuDTO.getButtons()) {
                    // 检查按钮编码是否为空
                    if (StrUtil.isBlank(btnDTO.getBtnCode())) {
                        continue; // 跳过btnCode为空的按钮
                    }
                    
                    SysBtn btn = BeanUtil.toBean(btnDTO, SysBtn.class);
                    btn.setMenuId(menu.getId());

                    if (btn.getId() != null) {
                        updateBtnList.add(btn);
                        updateBtnIds.add(btn.getId());
                    } else {
                        insertBtnList.add(btn);
                    }
                }
            }

            // 找出需要删除的按钮（当前菜单有但传入列表中没有的）
            List<Long> deleteBtnIds = currentBtnMap.keySet().stream()
                .filter(id -> !updateBtnIds.contains(id))
                .collect(Collectors.toList());
                
            // 删除按钮
            if (CollUtil.isNotEmpty(deleteBtnIds)) {
                sysBtnService.removeByIds(deleteBtnIds);
            }

            // 更新已有按钮
            if (CollUtil.isNotEmpty(updateBtnList)) {
                sysBtnService.updateBatchById(updateBtnList);
            }

            // 新增按钮
            if (CollUtil.isNotEmpty(insertBtnList)) {
                sysBtnService.saveBatch(insertBtnList);
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenuWithButtons(List<Long> ids) {
        // 删除菜单
        boolean result = removeBatchByIds(ids);

        // 删除关联的按钮
        if (result) {
            sysBtnService.remove(new LambdaQueryWrapper<SysBtn>().in(SysBtn::getMenuId, ids));

            // 删除角色菜单关联
            sysRoleMenuMapper.deleteRoleMenu(ids);
        }

        return result;
    }

    @Override
    public List<SysMenuVO> listAllMenus(SysMenuQuery query) {
        List<SysMenu> menuList = list(new LambdaQueryWrapper<SysMenu>()
            .like(StrUtil.isNotBlank(query.getRouteName()), SysMenu::getRouteName, query.getRouteName())
            .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
            .eq(query.getMenuType() != null, SysMenu::getMenuType, query.getMenuType())
            .orderByAsc(SysMenu::getOrder));

        return menuList.stream()
            .map(menu -> {
                SysMenuVO vo = BeanUtil.toBean(menu, SysMenuVO.class);
                // 手动处理枚举到Boolean的转换
                vo.setMultiTab(menu.getMultiTab() != null && menu.getMultiTab() == SysMenuMultiTabEnum.YES);
                vo.setActiveMenu(menu.getActiveMenu());
                return vo;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<MenuTreeVO> listAllMenusTree() {
        // 查询所有顶级菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, 0)
            .orderByAsc(SysMenu::getOrder);

        List<SysMenu> topMenus = list(wrapper);

        // 转换为树形结构
        return topMenus.stream()
            .map(menu -> {
                MenuTreeVO vo = BeanUtil.toBean(menu, MenuTreeVO.class);
                // 手动处理枚举到Boolean的转换
                vo.setMultiTab(menu.getMultiTab() != null && menu.getMultiTab() == SysMenuMultiTabEnum.YES);
                vo.setActiveMenu(menu.getActiveMenu());

                // 查询子菜单
                List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getParentId, menu.getId())
                    .orderByAsc(SysMenu::getOrder));

                if (CollUtil.isNotEmpty(children)) {
                    List<MenuTreeVO> childVOs = children.stream()
                        .map(child -> {
                            MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                            // 查询按钮信息
                            List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                                .eq(SysBtn::getMenuId, child.getId()));
                            childVO.setButtons(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                            return childVO;
                        })
                        .collect(Collectors.toList());
                    vo.setChildren(childVOs);
                }

                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                vo.setButtons(BeanUtil.copyToList(btnList, SysBtnVO.class));
                return vo;
            })
            .collect(Collectors.toList());
    }

    @Override
    public MenuTreeVO getMenuById(Long id) {
        // 查询菜单
        SysMenu menu = getById(id);
        if (menu == null) {
            return null;
        }

        // 转换为VO
        MenuTreeVO vo = BeanUtil.toBean(menu, MenuTreeVO.class);
        // 手动处理枚举到Boolean的转换
        vo.setMultiTab(menu.getMultiTab() != null && menu.getMultiTab() == SysMenuMultiTabEnum.YES);
        vo.setActiveMenu(menu.getActiveMenu());

        // 查询子菜单
        List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, menu.getId())
            .orderByAsc(SysMenu::getOrder));

        if (CollUtil.isNotEmpty(children)) {
            List<MenuTreeVO> childVOs = children.stream()
                .map(child -> {
                    MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                    // 手动处理枚举到Boolean的转换
                    childVO.setMultiTab(child.getMultiTab() != null && child.getMultiTab() == SysMenuMultiTabEnum.YES);
                    childVO.setActiveMenu(child.getActiveMenu());
                    // 查询按钮信息
                    List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                        .eq(SysBtn::getMenuId, child.getId()));
                    childVO.setButtons(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                    return childVO;
                })
                .collect(Collectors.toList());
            vo.setChildren(childVOs);
        }

        // 查询按钮信息
        List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
            .eq(SysBtn::getMenuId, menu.getId()));
        vo.setButtons(BeanUtil.copyToList(btnList, SysBtnVO.class));

        return vo;
    }

    @Override
    public Page<MenuTreeVO> pageMenuTree(SysMenuTreeQuery query) {
        // 只查询顶级菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, 0)
            .like(StrUtil.isNotBlank(query.getRouteName()), SysMenu::getRouteName, query.getRouteName())
            .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
            .eq(query.getMenuType() != null, SysMenu::getMenuType, query.getMenuType())
            .orderByAsc(SysMenu::getOrder);

        Page<SysMenu> page = query.toMpPage("`order`", true);
        page(page, wrapper);

        // 转换为VO
        List<MenuTreeVO> voList = page.getRecords().stream()
            .map(menu -> {
                MenuTreeVO vo = BeanUtil.toBean(menu, MenuTreeVO.class);
                // 手动处理枚举到Boolean的转换
                vo.setMultiTab(menu.getMultiTab() != null && menu.getMultiTab() == SysMenuMultiTabEnum.YES);
                vo.setActiveMenu(menu.getActiveMenu());

                // 查询子菜单
                List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getParentId, menu.getId())
                    .orderByAsc(SysMenu::getOrder));

                if (CollUtil.isNotEmpty(children)) {
                    List<MenuTreeVO> childVOs = children.stream()
                        .map(child -> {
                            MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                            // 查询按钮信息
                            List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                                .eq(SysBtn::getMenuId, child.getId()));
                            childVO.setButtons(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                            return childVO;
                        })
                        .collect(Collectors.toList());
                    vo.setChildren(childVOs);
                }
                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                vo.setButtons(BeanUtil.copyToList(btnList, SysBtnVO.class));
                return vo;
            })
            .collect(Collectors.toList());

        Page<MenuTreeVO> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(voList);

        return result;
    }

    @Override
    public List<RouteTreeVO> getCurrentUserRouteTree() {
        // 获取当前用户ID
        String userId = SecurityUtil.getUserId();

        // 查询用户菜单
        List<SysMenu> menuList = getMenusByUserId(userId);

        // 构建路由树
        return buildRouteTree(menuList, 0L);
    }

    @Override
    public List<RouteTreeVO> buildRouteTree(List<SysMenu> menuList, Long parentId) {
        List<RouteTreeVO> routeList = new ArrayList<>();

        for (SysMenu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                // 只复制基本字段到RouteTreeVO
                RouteTreeVO route = new RouteTreeVO();
                route.setId(menu.getId());
                route.setName(menu.getRouteName());
                route.setParentId(menu.getParentId());
                route.setPath(menu.getRoutePath());
                route.setComponent(menu.getComponent());

                // 构建元数据，将所有其他字段移到MenuMetaVO中
                RouteTreeVO.MenuMetaVO meta = new RouteTreeVO.MenuMetaVO();
                meta.setIcon(menu.getIcon());
                meta.setI18nKey(menu.getI18nKey());
                meta.setOrder(menu.getOrder());
                meta.setHref(menu.getHref());
                meta.setHideInMenu(menu.getHideInMenu() != null && menu.getHideInMenu());
                meta.setKeepAlive(menu.getKeepAlive() != null && menu.getKeepAlive());
                meta.setConstant(menu.getConstant() != null && menu.getConstant() == SysMenuConstantEnum.YES);
                meta.setMultiTab(menu.getMultiTab() != null && menu.getMultiTab() == SysMenuMultiTabEnum.YES);
                meta.setActiveMenu(menu.getActiveMenu());
                meta.setFixedIndexInTab(menu.getFixedIndexInTab());
                route.setMeta(meta);

                // 递归构建子路由
                route.setChildren(buildRouteTree(menuList, menu.getId()));

                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                route.setButtons(BeanUtil.copyToList(btnList, SysBtnVO.class));

                routeList.add(route);
            }
        }

        return routeList;
    }

    @Override
    public List<RouteTreeVO> getStaticMenuRouteTree() {
        // 查询所有静态菜单
        List<SysMenu> staticMenus = list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getConstant, true)
                .eq(SysMenu::getStatus, SysMenuStatusEnum.NORMAL)
                .orderByAsc(SysMenu::getOrder));

        if (CollUtil.isEmpty(staticMenus)) {
            return Collections.emptyList();
        }

        // 构建路由树
        return buildRouteTree(staticMenus, 0L);
    }
}
