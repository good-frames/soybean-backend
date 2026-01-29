package com.soybean.upms.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.soybean.common.core.annotation.ValidatedBy;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.common.security.annotation.RequirePermission;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.api.clients.SysUserClient;
import com.soybean.upms.api.dto.PasswordUpdateDTO;
import com.soybean.upms.api.dto.SysUserDTO;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.po.SysUser;
import com.soybean.upms.api.query.SysUserQuery;
import com.soybean.upms.api.vo.SysUserCreateResultVO;
import com.soybean.upms.api.vo.SysUserVO;
import com.soybean.upms.api.vo.UserInfoVO;
import com.soybean.upms.service.ISysUserService;
import com.soybean.upms.validator.SysUserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Tag(name = "用户管理", description = "系统用户的增删改查")
@Slf4j
@Data
@RestController
@RequestMapping("/user")
public class SysUserController implements SysUserClient {

    private final ISysUserService sysUserService;

    /**
     * 新增系统用户
     */
    @Operation(summary = "新增用户", description = "创建新的系统用户")
    @PostMapping
    @RequirePermission(value = "upms:user:list", orRole = "admin")
    public Result<SysUserCreateResultVO> add(
        @ValidatedBy(value = SysUserValidator.class, method = "addValidate") @RequestBody SysUserDTO sysUserDTO
    ) {
        try {
            SysUserCreateResultVO result = sysUserService.addSysUser(sysUserDTO);
            if (result != null) {
                return Result.ok(result);
            } else {
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除系统用户
     */
    @Operation(summary = "删除用户", description = "批量删除系统用户")
    @DeleteMapping
    @RequirePermission(value = "upms:user:list", orRole = "admin")
    public Result<Void> delete(@RequestBody List<String> idList) {
        try {
            
            // 检查是否包含admin账号(ID为1)，不允许删除admin账号
            if (idList.contains("1")) {
                return Result.fail("不允许删除admin账号");
            }
            
            if (sysUserService.deleteSysUsers(idList)) {
                return Result.ok();
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改系统用户
     */
    @Operation(summary = "修改用户", description = "修改系统用户信息")
    @PutMapping
    @RequirePermission(value = "upms:user:list", orRole = "admin")
    public Result<Void> update(
        @ValidatedBy(value = SysUserValidator.class, method = "updateValidate") @RequestBody SysUserDTO sysUserDTO
    ) {
        try {
            if (sysUserService.updateSysUser(sysUserDTO)) {
                return Result.ok();
            } else {
                return Result.fail("更新失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改状态
     */
    @Operation(summary = "修改用户状态", description = "启用或停用用户")
    @PutMapping("/status/{id}/{status}")
    @RequirePermission(value = "upms:user:list", orRole = "admin")
    public Result<Void> updateStatus(@Parameter(description = "用户ID") @PathVariable String id, @Parameter(description = "状态") @PathVariable String status) {
        try {
            // 检查是否为admin账号(ID为1)，不允许修改admin账号状态
            if ("1".equals(id)) {
                return Result.fail("不允许修改admin账号状态");
            }

            if (sysUserService.updateSysUserStatus(id, StatusEnum.fromValue(status))) {
                return Result.ok();
            } else {
                return Result.fail("状态更新失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码", description = "用户修改自己的密码")
    @PutMapping("/password")
    @SaCheckOr(
            login = @SaCheckLogin,
            role = @SaCheckRole("admin"),
            permission = @SaCheckPermission("upms:user:list")
    )
    public Result<Void> updatePassword(@ValidatedBy(value = SysUserValidator.class, method = "updatePasswordValidate") @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
            if (sysUserService.updatePassword(passwordUpdateDTO)) {
                return Result.ok();
            } else {
                return Result.fail("密码修改失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 管理员修改用户密码
     */
    @Operation(summary = "管理员修改密码", description = "管理员重置用户密码")
    @PutMapping("/password/admin")
    @SaCheckPermission(value = "upms:user:list", orRole = "admin")
    public Result<Void> adminUpdatePassword(@ValidatedBy(value = SysUserValidator.class, method = "updatePasswordValidate") @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
            if (sysUserService.adminUpdatePassword(passwordUpdateDTO)) {
                return Result.ok();
            } else {
                return Result.fail("密码修改失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据ID查询系统用户
     */
    @Operation(summary = "获取用户详情", description = "根据用户ID查询用户信息")
    @GetMapping("/{id}")
    @SaCheckPermission(value = "upms:user:list", orRole = "admin")
    public Result<SysUserVO> getById(@Parameter(description = "用户ID") @PathVariable String id) {
        try {
            SysUserVO sysUserVO = sysUserService.getSysUserVOById(id);
            if (sysUserVO != null) {
                return Result.ok(sysUserVO);
            } else {
                return Result.fail("用户不存在");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 分页查询系统用户列表
     */
    @Operation(summary = "分页查询用户", description = "分页查询系统用户列表")
    @GetMapping("/page")
    @SaCheckPermission(value = "upms:user:list", orRole = "admin")
    public Result<PageDTO<SysUserVO>> page(SysUserQuery query) {
        try {
            PageDTO<SysUserVO> userPage = sysUserService.getSysUserPage(query);
            return Result.ok(userPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询所有系统用户
     */
    @Operation(summary = "查询用户列表", description = "查询所有系统用户")
    @GetMapping("/list")
    @SaCheckPermission(value = "upms:user:list", orRole = "admin")
    public Result<List<SysUserVO>> list() {
        try {
            List<SysUserVO> list = sysUserService.getAllSysUsers();
            return Result.ok(list);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据用户名获取用户信息
     */
    @Operation(summary = "根据用户名查询", description = "根据用户名获取用户信息")
    @GetMapping("/getByUsername")
    public Result<SysUser> getByUsername(@Parameter(description = "用户名") @RequestParam String username) {
        try {
            return Result.ok(sysUserService.getUserByUsername(username));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前登录用户信息（包括基本信息、角色和权限）
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息（包括基本信息、角色和权限）")
    @GetMapping("/info/current")
    public Result<UserInfoVO> getCurrentUserInfo() {
        try {
            // 获取当前登录用户ID
            String userId = SecurityUtil.getUserId();
            // 获取用户信息（包括角色和权限）
            UserInfoVO userInfo = sysUserService.getCurrentUserInfo(userId);
            if (userInfo != null) {
                return Result.ok(userInfo);
            } else {
                return Result.fail("获取用户信息失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
