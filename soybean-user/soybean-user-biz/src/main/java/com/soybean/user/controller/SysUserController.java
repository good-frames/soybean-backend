package com.soybean.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckOr;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.common.security.annotation.RequireLogin;
import com.soybean.common.security.annotation.RequirePermission;
import com.soybean.user.api.clients.SysUserClient;
import com.soybean.user.api.dto.SysUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.po.SysUser;
import com.soybean.user.api.query.SysUserQuery;
import com.soybean.user.api.vo.SysUserVO;
import com.soybean.common.core.annotation.ValidatedBy;
import com.soybean.user.service.ISysUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Slf4j
@Data
@RestController
@RequestMapping("/user/admin")
public class SysUserController implements SysUserClient {

    private final ISysUserService sysUserService;

    /**
     * 新增系统用户
     */
    @PostMapping
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<Void> add(@ValidatedBy("sysUserValidator") @RequestBody SysUserDTO sysUserDTO) {

        try {
            if (sysUserService.addSysUser(sysUserDTO)) {
                return Result.ok();
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
    @DeleteMapping("/{ids}")
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<Void> delete(@PathVariable String ids) {
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            
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
    @PutMapping
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<Void> update(@ValidatedBy("sysUserValidator") @RequestBody SysUserDTO sysUserDTO) {
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
     * 根据ID查询系统用户
     */
    @GetMapping("/{id}")
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<SysUserVO> getById(@PathVariable String id) {
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
    @GetMapping("/page")
    @RequirePermission(value = "system:user:list", orRole = "admin")
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
    @GetMapping("/list")
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<List<SysUserVO>> list() {
        try {
            List<SysUserVO> list = sysUserService.getAllSysUsers();
            return Result.ok(list);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改状态
     */
    @PutMapping("/status/{id}/{status}")
    @RequirePermission(value = "system:user:list", orRole = "admin")
    public Result<Void> updateStatus(@PathVariable String id, @PathVariable String status) {
        try {
            // 检查是否为admin账号(ID为1)，不允许修改admin账号状态
            if ("1".equals(id)) {
                return Result.fail("不允许修改admin账号状态");
            }
            
            if (sysUserService.updateSysUserStatus(id, SysUserStatusEnum.fromValue(status))) {
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
    @PutMapping("/password")
    @SaCheckOr(
            login = @SaCheckLogin,
            role = @SaCheckRole("admin"),
            permission = @SaCheckPermission("system:user:list")
    )
    public Result<Void> updatePassword(@Validated @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
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
     * 根据用户名获取用户信息
     */
    @GetMapping("/getByUsername")
    public Result<SysUser> getByUsername(@RequestParam String username) {
        try {
            return Result.ok(sysUserService.getUserByUsername(username));
        } catch (Exception e) {
            return null;
        }
    }
}
