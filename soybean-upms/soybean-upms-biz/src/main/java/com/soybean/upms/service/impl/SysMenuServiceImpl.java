package com.soybean.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.api.dto.SysMenuDTO;
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
                .orderByAsc(SysMenu::getOrderNum));
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
        // 转换DTO为PO
        SysMenu menu = BeanUtil.toBean(menuDTO, SysMenu.class);
        boolean result = save(menu);

        // 保存按钮信息
        if (result && CollUtil.isNotEmpty(menuDTO.getBtnList())) {
            List<SysBtn> btnList = menuDTO.getBtnList().stream()
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
        // 更新菜单信息
        SysMenu menu = BeanUtil.toBean(menuDTO, SysMenu.class);
        boolean result = updateById(menu);

        // 处理按钮信息
        if (result && CollUtil.isNotEmpty(menuDTO.getBtnList())) {
            List<SysBtn> updateBtnList = new ArrayList<>();
            List<SysBtn> insertBtnList = new ArrayList<>();

            for (SysBtnDTO btnDTO : menuDTO.getBtnList()) {
                SysBtn btn = BeanUtil.toBean(btnDTO, SysBtn.class);
                btn.setMenuId(menu.getId());

                if (btn.getId() != null) {
                    updateBtnList.add(btn);
                } else {
                    insertBtnList.add(btn);
                }
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
            .like(StrUtil.isNotBlank(query.getName()), SysMenu::getName, query.getName())
            .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
            .eq(query.getType() != null, SysMenu::getType, query.getType())
            .orderByAsc(SysMenu::getOrderNum));

        return menuList.stream()
            .map(menu -> {
                return BeanUtil.toBean(menu, SysMenuVO.class);
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<MenuTreeVO> listAllMenusTree() {
        // 查询所有顶级菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, 0)
            .orderByAsc(SysMenu::getOrderNum);

        List<SysMenu> topMenus = list(wrapper);

        // 转换为树形结构
        return topMenus.stream()
            .map(menu -> {
                MenuTreeVO vo = BeanUtil.toBean(menu, MenuTreeVO.class);

                // 查询子菜单
                List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getParentId, menu.getId())
                    .orderByAsc(SysMenu::getOrderNum));

                if (CollUtil.isNotEmpty(children)) {
                    List<MenuTreeVO> childVOs = children.stream()
                        .map(child -> {
                            MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                            // 查询按钮信息
                            List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                                .eq(SysBtn::getMenuId, child.getId()));
                            childVO.setBtnList(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                            return childVO;
                        })
                        .collect(Collectors.toList());
                    vo.setChildren(childVOs);
                }

                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                vo.setBtnList(BeanUtil.copyToList(btnList, SysBtnVO.class));
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

        // 查询子菜单
        List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, menu.getId())
            .orderByAsc(SysMenu::getOrderNum));

        if (CollUtil.isNotEmpty(children)) {
            List<MenuTreeVO> childVOs = children.stream()
                .map(child -> {
                    MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                    // 查询按钮信息
                    List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                        .eq(SysBtn::getMenuId, child.getId()));
                    childVO.setBtnList(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                    return childVO;
                })
                .collect(Collectors.toList());
            vo.setChildren(childVOs);
        }

        // 查询按钮信息
        List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
            .eq(SysBtn::getMenuId, menu.getId()));
        vo.setBtnList(BeanUtil.copyToList(btnList, SysBtnVO.class));

        return vo;
    }

    @Override
    public Page<MenuTreeVO> pageMenuTree(SysMenuTreeQuery query) {
        // 只查询顶级菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getParentId, 0)
            .like(StrUtil.isNotBlank(query.getName()), SysMenu::getName, query.getName())
            .eq(query.getStatus() != null, SysMenu::getStatus, query.getStatus())
            .eq(query.getType() != null, SysMenu::getType, query.getType())
            .orderByAsc(SysMenu::getOrderNum);

        Page<SysMenu> page = query.toMpPage("order_num", true);
        page(page, wrapper);

        // 转换为VO
        List<MenuTreeVO> voList = page.getRecords().stream()
            .map(menu -> {
                MenuTreeVO vo = BeanUtil.toBean(menu, MenuTreeVO.class);

                // 查询子菜单
                List<SysMenu> children = list(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getParentId, menu.getId())
                    .orderByAsc(SysMenu::getOrderNum));

                if (CollUtil.isNotEmpty(children)) {
                    List<MenuTreeVO> childVOs = children.stream()
                        .map(child -> {
                            MenuTreeVO childVO = BeanUtil.toBean(child, MenuTreeVO.class);
                            // 查询按钮信息
                            List<SysBtn> childBtnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                                .eq(SysBtn::getMenuId, child.getId()));
                            childVO.setBtnList(BeanUtil.copyToList(childBtnList, SysBtnVO.class));
                            return childVO;
                        })
                        .collect(Collectors.toList());
                    vo.setChildren(childVOs);
                }
                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                vo.setBtnList(BeanUtil.copyToList(btnList, SysBtnVO.class));
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
                RouteTreeVO route = BeanUtil.toBean(menu, RouteTreeVO.class);

                // 构建元数据
                RouteTreeVO.MenuMetaVO meta = new RouteTreeVO.MenuMetaVO();
                meta.setTitle(menu.getTitle());
                meta.setIcon(menu.getIcon());
                meta.setI18nKey(menu.getI18nKey());
                meta.setOrder(menu.getOrderNum());
                route.setMeta(meta);

                // 递归构建子路由
                route.setChildren(buildRouteTree(menuList, menu.getId()));

                // 查询按钮信息
                List<SysBtn> btnList = sysBtnService.list(new LambdaQueryWrapper<SysBtn>()
                    .eq(SysBtn::getMenuId, menu.getId()));
                route.setBtnList(BeanUtil.copyToList(btnList, SysBtnVO.class));

                routeList.add(route);
            }
        }

        return routeList;
    }
}
