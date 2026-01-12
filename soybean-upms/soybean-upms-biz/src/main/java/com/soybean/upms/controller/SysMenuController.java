
package com.soybean.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soybean.common.core.utils.Result;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.api.query.SysMenuTreeQuery;
import com.soybean.upms.api.vo.MenuTreeVO;
import com.soybean.upms.api.vo.RouteTreeVO;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.vo.UserRouteResultVO;
import com.soybean.upms.api.po.SysRole;
import com.soybean.upms.service.ISysMenuService;
import com.soybean.upms.service.ISysRoleService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限Controller
 *
 * @author soybean
 * @since 2024-07-07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/systemManage/menu")
public class SysMenuController {

    private final ISysMenuService menuService;
    private final ISysRoleService roleService;

    /**
     * 新增菜单
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysMenuDTO menuDTO) {
        boolean result = menuService.saveMenuWithButtons(menuDTO);
        return result ? Result.ok() : Result.fail();
    }

    /**
     * 编辑菜单
     */
    @PutMapping
    public Result<Void> update(@Validated @RequestBody SysMenuDTO menuDTO) {
        boolean result = menuService.updateMenuWithButtons(menuDTO);
        return result ? Result.ok() : Result.fail();
    }

    /**
     * 批量删除菜单
     */
    @DeleteMapping
    public Result<Void> remove(@RequestBody List<Long> ids) {
        boolean result = menuService.deleteMenuWithButtons(ids);
        return result ? Result.ok() : Result.fail();
    }

    /**
     * 查询所有菜单
     */
    @GetMapping("/list")
    public Result<List<SysMenuVO>> list(SysMenuQuery query) {
        List<SysMenuVO> voList = menuService.listAllMenus(query);
        return Result.ok(voList);
    }

    /**
     * 查询所有菜单树
     */
    @GetMapping("/tree")
    public Result<List<MenuTreeVO>> tree() {
        List<MenuTreeVO> treeList = menuService.listAllMenusTree();
        return Result.ok(treeList);
    }

    /**
     * 根据菜单ID查询菜单详情
     */
    @GetMapping("/{id}")
    public Result<MenuTreeVO> getById(@PathVariable Long id) {
        MenuTreeVO menu = menuService.getMenuById(id);
        return Result.ok(menu);
    }

    /**
     * 分页查询菜单树
     */
    @GetMapping("/page")
    public Result<Page<MenuTreeVO>> page(SysMenuTreeQuery query) {
        Page<MenuTreeVO> result = menuService.pageMenuTree(query);
        return Result.ok(result);
    }

    /**
     * 获取当前登录者拥有的路由树
     */
    @GetMapping("/route")
    public Result<UserRouteResultVO> route() {
        List<RouteTreeVO> routeList = menuService.getCurrentUserRouteTree();
        UserRouteResultVO resultVO = new UserRouteResultVO();
        resultVO.setRoutes(routeList);
        
        // 获取当前用户的角色ID
        String userId = SecurityUtil.getUserId();
        List<Long> roleIds = roleService.getRoleIdsByUserId(userId);
        
        // 获取角色的home字段，如果有多个角色，取第一个角色的home
        if (CollUtil.isNotEmpty(roleIds)) {
            Long roleId = roleIds.get(0);
            SysRole role = roleService.getById(roleId);
            if (role != null && StrUtil.isNotBlank(role.getHome())) {
                resultVO.setHome(role.getHome());
            } else {
                // 如果没有设置home，使用默认值
                resultVO.setHome("home");
            }
        } else {
            // 如果没有角色，使用默认值
            resultVO.setHome("home");
        }
        
        return Result.ok(resultVO);
    }

    /**
     * 根据用户ID获取用户的权限
     */
    @GetMapping("/permission/user/{userId}")
    public Result<List<String>> getCurrentUserPermissions(@PathVariable String userId) {
        List<String> permissions = menuService.getPermissionsByUserId(userId);
        return Result.ok(permissions);
    }

    /**
     * 获取所有静态菜单路由树
     */
    @GetMapping("/static")
    public Result<List<RouteTreeVO>> getStaticMenuRouteTree() {
        List<RouteTreeVO> routeList = menuService.getStaticMenuRouteTree();
        return Result.ok(routeList);
    }

}
