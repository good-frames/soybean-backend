package com.soybean.user.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.user.api.dto.AdminUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import com.soybean.user.api.query.AdminUserQuery;
import com.soybean.user.api.vo.AdminUserVO;
import com.soybean.common.core.annotation.ValidatedBy;
import com.soybean.user.service.IAdminUserService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台管理员表 前端控制器
 * </p>
 *
 * @author dongdongjie
 * @since 2025-12-16
 */
@Data
@RestController
@RequestMapping("/user/admin")
public class AdminUserController {

    private final IAdminUserService adminUserService;

    /**
     * 新增管理员
     */
    @PostMapping
    public Result<Void> add(@ValidatedBy("adminUserValidator") @RequestBody AdminUserDTO adminUserDTO) {
        
        try {
            if (adminUserService.addAdminUser(adminUserDTO)) {
                return Result.ok();
            } else {
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("/{ids}")
    public Result<Void> delete(@PathVariable String ids) {
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            if (adminUserService.deleteAdminUsers(idList)) {
                return Result.ok();
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改管理员
     */
    @PutMapping
    public Result<Void> update(@ValidatedBy("adminUserValidator") @RequestBody AdminUserDTO adminUserDTO) {
        try {
            if (adminUserService.updateAdminUser(adminUserDTO)) {
                return Result.ok();
            } else {
                return Result.fail("更新失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据ID查询管理员
     */
    @GetMapping("/{id}")
    public Result<AdminUserVO> getById(@PathVariable String id) {
        try {
            AdminUserVO adminUserVO = adminUserService.getAdminUserVOById(id);
            if (adminUserVO != null) {
                return Result.ok(adminUserVO);
            } else {
                return Result.fail("用户不存在");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 分页查询管理员列表
     */
    @GetMapping("/page")
    public Result<PageDTO<AdminUserVO>> page(AdminUserQuery query) {
        try {
            PageDTO<AdminUserVO> userPage = adminUserService.getAdminUserPage(query);
            return Result.ok(userPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询所有管理员
     */
    @GetMapping("/list")
    public Result<List<AdminUserVO>> list() {
        try {
            List<AdminUserVO> list = adminUserService.getAllAdminUsers();
            return Result.ok(list);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改状态
     */
    @PutMapping("/status/{id}/{status}")
    public Result<Void> updateStatus(@PathVariable String id, @PathVariable Integer status) {
        try {
            if (adminUserService.updateAdminUserStatus(id, AdminUserStatusEnum.valueOf(status))) {
                return Result.ok();
            } else {
                return Result.fail("状态更新失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result<AdminUserVO> login(@Valid @RequestBody AdminUserDTO adminUserDTO, BindingResult bindingResult) {
        // 验证参数
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return Result.fail(errorMsg);
        }
        
        try {
            AdminUserVO adminUserVO = adminUserService.login(adminUserDTO);
            return Result.ok(adminUserVO);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Void> updatePassword(@Validated @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
            if (adminUserService.updatePassword(passwordUpdateDTO)) {
                return Result.ok();
            } else {
                return Result.fail("密码修改失败");
            }
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
