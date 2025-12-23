
package com.soybean.upms.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.vo.SysRoleVO;
import com.soybean.upms.service.ISysMenuService;
import com.soybean.upms.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 权限控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@RestController
@RequestMapping("/sys/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final ISysRoleService roleService;
    private final ISysMenuService menuService;

    /**
     * 根据用户ID获取角色列表
     */
    @GetMapping("/role/user/{userId}")
    public Result<List<SysRoleVO>> getRolesByUserId(@PathVariable Long userId) {
        List<SysRoleVO> roles = roleService.selectRolesByUserId(userId);
        return Result.ok(roles);
    }

    /**
     * 根据用户ID获取权限集合
     */
    @GetMapping("/menu/perms/{userId}")
    public Set<String> getPermissionsByUserId(@PathVariable Long userId) {
        return menuService.selectPermsByUserId(userId);
    }

    /**
     * 根据用户ID获取菜单列表
     */
    @GetMapping("/menu/user/{userId}")
    public List<SysMenuVO> getMenusByUserId(@PathVariable Long userId) {
        return menuService.selectMenuList(userId);
    }

    /**
     * 根据角色ID获取权限集合
     */
    @GetMapping("/menu/perms/role/{roleId}")
    public Set<String> getPermissionsByRoleId(@PathVariable Long roleId) {
        return menuService.selectPermsByRoleId(roleId);
    }

    /**
     * 判断用户是否有某个权限
     */
    @GetMapping("/menu/check-permission")
    public Boolean checkPermission(@RequestParam Long userId, @RequestParam String permission) {
        Set<String> permissions = menuService.selectPermsByUserId(userId);
        return permissions.contains(permission);
    }
}
