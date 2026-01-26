
package com.soybean.upms.controller;

import cn.hutool.core.bean.BeanUtil;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.clients.SysRoleClient;
import com.soybean.upms.api.dto.RoleMenuBtnBindDTO;
import com.soybean.upms.api.dto.SysRoleDTO;
import com.soybean.upms.api.dto.UserRoleBindDTO;
import com.soybean.upms.api.vo.RoleMenuBtnVO;
import com.soybean.upms.api.vo.SysRoleVO;
import com.soybean.upms.api.query.SysRoleQuery;
import com.soybean.upms.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@Tag(name = "角色管理", description = "角色信息的增删改查")
@RestController
@RequestMapping("/systemManage/role")
@RequiredArgsConstructor
public class SysRoleController implements SysRoleClient {

    private final ISysRoleService roleService;

    /**
     * 新增角色
     */
    @Operation(summary = "新增角色", description = "创建新的系统角色")
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysRoleDTO roleDTO) {
        if (roleService.checkRoleNameUnique(roleDTO)) {
            return Result.fail("新增角色'" + roleDTO.getRoleName() + "'失败，角色名称已存在");
        } else if (roleService.checkRoleKeyUnique(roleDTO)) {
            return Result.fail("新增角色'" + roleDTO.getRoleName() + "'失败，角色权限已存在");
        }
        return roleService.insertRole(roleDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 删除角色
     */
    @Operation(summary = "删除角色", description = "批量删除角色")
    @DeleteMapping
    public Result<Void> remove(@RequestBody List<Long> roleIds) {
        try {
            return roleService.deleteRoleByIds(roleIds) ? Result.ok() : Result.fail();
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改保存角色（不修改菜单关联）
     */
    @Operation(summary = "修改角色", description = "修改角色基本信息")
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysRoleDTO roleDTO) {
        roleService.checkRoleAllowed(roleDTO);

        if (roleService.checkRoleNameUnique(roleDTO)) {
            return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，角色名称已存在");
        } else if (roleService.checkRoleKeyUnique(roleDTO)) {
            return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(roleDTO)) {
            return Result.ok();
        }
        return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，请联系管理员");
    }
    /**
     * 状态修改
     */
    @Operation(summary = "修改角色状态", description = "启用或停用角色")
    @PutMapping("/changeStatus")
    public Result<Void> changeStatus(@RequestBody SysRoleDTO roleDTO) {
        roleService.checkRoleAllowed(roleDTO);

        try {
            return roleService.updateRoleStatus(roleDTO.getId(), roleDTO.getStatus()) ? Result.ok() : Result.fail();
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 获取所有角色
     */
    @Operation(summary = "分页查询角色", description = "根据条件分页查询角色列表")
    @GetMapping("/page")
    public Result<PageDTO<SysRoleVO>> page(SysRoleQuery query) {
        PageDTO<SysRoleVO> result = roleService.getRolePage(query);
        return Result.ok(result);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @Operation(summary = "获取角色详情", description = "根据角色ID获取角色详细信息")
    @GetMapping(value = "/{roleId}")
    public Result<SysRoleVO> getInfo(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        return Result.ok(BeanUtil.copyProperties(roleService.getById(roleId), SysRoleVO.class));
    }

    /**
     * 获取角色选择框列表
     */
    @Operation(summary = "获取所有角色", description = "获取所有角色列表，用于下拉选择框")
    @GetMapping("/getAllRoles")
    public Result<List<SysRoleVO>> allList() {
        return Result.ok(roleService.selectRoleAll());
    }

    /**
     * 根据用户ID获取角色列表
     */
    @Operation(summary = "获取用户角色", description = "根据用户ID获取该用户的角色列表")
    @GetMapping("/user/{userId}")
    public Result<List<SysRoleVO>> getRolesByUserId(@Parameter(description = "用户ID") @PathVariable String userId) {
        List<SysRoleVO> roles = roleService.selectRolesByUserId(userId);
        return Result.ok(roles);
    }

    /**
     * 根据用户ID获取角色权限字符串列表
     */
    @Operation(summary = "获取用户角色权限", description = "根据用户ID获取该用户的角色权限字符串列表")
    @GetMapping("/user/{userId}/keys")
    public Result<List<String>> getRoleKeysByUserId(@Parameter(description = "用户ID") @PathVariable String userId) {
        List<String> roleKeys = roleService.selectRoleKeysByUserId(userId);
        return Result.ok(roleKeys);
    }

    /**
     * 获取角色绑定的菜单及按钮
     */
    @Operation(summary = "获取角色菜单按钮", description = "获取角色绑定的菜单及按钮权限")
    @GetMapping("/{roleId}/menuBtn")
    public Result<RoleMenuBtnVO> getRoleMenuBtn(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        RoleMenuBtnVO roleMenuBtn = roleService.getRoleMenuBtn(roleId);
        return Result.ok(roleMenuBtn);
    }

    /**
     * 绑定角色和菜单、按钮
     */
    @Operation(summary = "绑定角色菜单按钮", description = "为角色绑定菜单和按钮权限")
    @PostMapping("/{roleId}/bindMenuBtn")
    public Result<Boolean> bindRoleMenuBtn(@Parameter(description = "角色ID") @PathVariable Long roleId, @RequestBody @Validated RoleMenuBtnBindDTO bindDTO) {
        boolean result = roleService.bindRoleMenuBtn(roleId, bindDTO);
        return Result.ok(result);
    }

    /**
     * 为用户绑定角色
     */
    @Operation(summary = "绑定用户角色", description = "为用户绑定角色")
    @PostMapping("/bindUserRole")
    public Result<Boolean> bindUserRole(@RequestBody @Validated UserRoleBindDTO bindDTO) {
        boolean result = roleService.bindUserRole(bindDTO);
        return Result.ok(result);
    }
}
