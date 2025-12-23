
package com.soybean.upms.api.clients;

import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.vo.SysRoleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 权限服务接口
 *
 * @author soybean
 * @since 2024-07-07
 */
@FeignClient(contextId = "sysPermissionClient", value = "soybean-upms")
public interface SysPermissionClient {

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @GetMapping("/role/user/{userId}")
    List<SysRoleVO> getRolesByUserId(@PathVariable("userId") Long userId);

    /**
     * 根据用户ID获取权限集合
     *
     * @param userId 用户ID
     * @return 权限集合
     */
    @GetMapping("/menu/perms/{userId}")
    Set<String> getPermissionsByUserId(@PathVariable("userId") Long userId);

    /**
     * 根据用户ID获取菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @GetMapping("/menu/user/{userId}")
    List<SysMenuVO> getMenusByUserId(@PathVariable("userId") Long userId);

    /**
     * 根据角色ID获取权限集合
     *
     * @param roleId 角色ID
     * @return 权限集合
     */
    @GetMapping("/menu/perms/role/{roleId}")
    Set<String> getPermissionsByRoleId(@PathVariable("roleId") Long roleId);

    /**
     * 判断用户是否有某个权限
     *
     * @param userId 用户ID
     * @param permission 权限标识
     * @return 是否有权限
     */
    @GetMapping("/menu/check-permission")
    Boolean checkPermission(@RequestParam("userId") Long userId, @RequestParam("permission") String permission);
}
