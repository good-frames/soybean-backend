
package com.soybean.upms.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.api.clients.SysMenuClient;
import com.soybean.upms.api.dto.RoleIdsDTO;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.vo.UserMenuVO;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单信息控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@Slf4j
@RestController
@RequestMapping("/upms/menu")
@RequiredArgsConstructor
public class SysMenuController implements SysMenuClient {

    private final ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public Result<List<SysMenuVO>> list(SysMenuQuery query) {
        List<SysMenuVO> menus = menuService.selectMenuList(query, null);
        return Result.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public Result<SysMenuVO> getInfo(@PathVariable Long menuId) {
        SysMenuVO menuVO = menuService.selectMenuById(menuId);
        if (menuVO == null) {
            return Result.fail("菜单不存在");
        }
        return Result.ok(menuVO);
    }

    /**
     * 新增菜单
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysMenuDTO menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return Result.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        return menuService.insertMenu(menu) ? Result.ok() : Result.fail();
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysMenuDTO menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return Result.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return Result.fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return menuService.updateMenu(menu) ? Result.ok() : Result.fail();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public Result<Void> remove(@PathVariable Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return Result.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return Result.fail("菜单已分配,不允许删除");
        }
        return menuService.deleteMenuById(menuId) ? Result.ok() : Result.fail();
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    public Result<List<SysMenuVO>> treeList(SysMenuQuery query) {
        List<SysMenuVO> menus = menuService.selectMenuList(query, null);
        return Result.ok(menuService.buildMenuTreeSelect(menus));
    }


    /**
     * 获取多个角色关联的菜单列表（扁平化）
     */
    @PostMapping(value = "/roleMenuFlatList")
    public Result<List<SysMenuVO>> roleMenuFlatList(@Validated @RequestBody RoleIdsDTO roleIdsDTO) {
        List<Long> roleIds = roleIdsDTO.getRoleIds();
        Long[] roleIdArray = roleIds.toArray(new Long[0]);
        List<SysMenuVO> menus = menuService.selectMenuFlatListByRoleIds(roleIdArray);
        return Result.ok(menus);
    }

    /**
     * 获取当前登录用户所拥有的权限集合
     */
    @GetMapping("/permission/user/{userId}")
    public Result<List<String>> getCurrentUserPermissions(@PathVariable String userId) {
        return Result.ok(menuService.selectPermissionsByUserId(userId));
    }

    /**
     * 获取当前登录用户拥有的菜单列表（包括目录、菜单、按钮）
     */
    @GetMapping("/user/current")
    public Result<UserMenuVO> getCurrentUserMenus() {
        // 获取当前登录用户ID
        String userId = SecurityUtil.getUserId();
        // 查询用户菜单列表
        List<SysMenuVO> menus = menuService.selectMenuList(userId);
        // 构建树形结构
        List<SysMenuVO> menuTree = menuService.buildMenuTree(menus);
        
        // 创建用户菜单VO
        UserMenuVO userMenu = new UserMenuVO();
        userMenu.setRoutes(menuTree);
        userMenu.setHome("home");
        
        return Result.ok(userMenu);
    }
}
