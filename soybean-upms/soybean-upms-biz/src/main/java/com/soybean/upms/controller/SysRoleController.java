
package com.soybean.upms.controller;

import cn.hutool.core.bean.BeanUtil;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysRoleDTO;
import com.soybean.upms.api.vo.SysRoleVO;
import com.soybean.upms.api.query.SysRoleQuery;
import com.soybean.upms.service.ISysRoleService;
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
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService roleService;

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public Result<PageDTO<SysRoleVO>> list(SysRoleQuery query) {
        PageDTO<SysRoleVO> result = roleService.getRolePage(query);
        return Result.ok(result);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public Result<SysRoleVO> getInfo(@PathVariable Long roleId) {
        return Result.ok(BeanUtil.copyProperties(roleService.getById(roleId), SysRoleVO.class));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysRoleDTO roleDTO) {
        if (!roleService.checkRoleNameUnique(roleDTO)) {
            return Result.fail("新增角色'" + roleDTO.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(roleDTO)) {
            return Result.fail("新增角色'" + roleDTO.getRoleName() + "'失败，角色权限已存在");
        }
        return roleService.insertRole(roleDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 修改保存角色
     */
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysRoleDTO roleDTO) {
        roleService.checkRoleAllowed(roleDTO);

        if (!roleService.checkRoleNameUnique(roleDTO)) {
            return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(roleDTO)) {
            return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(roleDTO)) {
            return Result.ok();
        }
        return Result.fail("修改角色'" + roleDTO.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    @PutMapping("/dataScope")
    public Result<Void> dataScope(@RequestBody SysRoleDTO roleDTO) {
        return roleService.authDataScope(roleDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public Result<Void> changeStatus(@RequestBody SysRoleDTO roleDTO) {
        roleService.checkRoleAllowed(roleDTO);
        return roleService.updateRoleStatus(roleDTO.getRoleId(), roleDTO.getStatus()) ? Result.ok() : Result.fail();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    public Result<Void> remove(@PathVariable List<Long> roleIds) {
        return roleService.deleteRoleByIds(roleIds) ? Result.ok() : Result.fail();
    }

    /**
     * 获取角色选择框列表
     */
    @GetMapping("/optionselect")
    public Result<List<SysRoleVO>> optionselect() {
        return Result.ok(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public Result<PageDTO<SysRoleVO>> allocatedList(SysRoleQuery query) {
        PageDTO<SysRoleVO> result = roleService.getRolePage(query);
        return Result.ok(result);
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public Result<PageDTO<SysRoleVO>> unallocatedList(SysRoleQuery query) {
        PageDTO<SysRoleVO> result = roleService.getRolePage(query);
        return Result.ok(result);
    }

    /**
     * 取消授权用户角色
     */
    @PutMapping("/authUser/cancel")
    public Result<Void> cancelAuthUser(@RequestBody SysRoleDTO roleDTO) {
        return Result.ok();
    }

    /**
     * 批量取消授权用户角色
     */
    @PutMapping("/authUser/cancelAll")
    public Result<Void> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return Result.ok();
    }

    /**
     * 批量选择用户授权
     */
    @PutMapping("/authUser/selectAll")
    public Result<Void> selectAuthUserAll(Long roleId, Long[] userIds) {
        return Result.ok();
    }
}
