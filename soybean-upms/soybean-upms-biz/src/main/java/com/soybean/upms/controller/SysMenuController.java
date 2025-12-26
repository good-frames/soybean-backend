
package com.soybean.upms.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {

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
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    public Result<List<SysMenuVO>> treeList(SysMenuQuery query) {
        List<SysMenuVO> menus = menuService.selectMenuList(query, null);
        return Result.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public Result<Object> roleMenuTreeselect(@PathVariable Long roleId) {
        List<SysMenuVO> menus = menuService.selectMenuList(null, null);
        Map<String, Object> data = new HashMap<>();
        data.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        data.put("menus", menuService.buildMenuTreeSelect(menus));
        return Result.ok(data);
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
}
