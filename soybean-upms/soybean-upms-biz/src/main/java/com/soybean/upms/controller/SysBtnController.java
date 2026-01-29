package com.soybean.upms.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.service.ISysBtnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 按钮权限Controller
 *
 * @author soybean
 * @since 2026-01-04
 */
@Tag(name = "按钮权限管理", description = "按钮权限相关接口")
@RestController
@RequestMapping("/btn")
@RequiredArgsConstructor
public class SysBtnController {

    private final ISysBtnService btnService;

    /**
     * 查询按钮列表
     */
    @Operation(summary = "查询按钮列表", description = "根据条件查询按钮列表")
    @GetMapping("/list")
    public Result<List<SysBtnDTO>> list(SysBtnDTO btnDTO) {
        return Result.ok(btnService.selectBtnList(btnDTO));
    }

    /**
     * 根据菜单ID查询按钮列表
     */
    @Operation(summary = "根据菜单ID查询按钮列表", description = "根据菜单ID查询该菜单下的所有按钮")
    @GetMapping("/listByMenuId/{menuId}")
    public Result<List<SysBtnDTO>> listByMenuId(@Parameter(description = "菜单ID", required = true) @PathVariable Long menuId) {
        return Result.ok(btnService.selectBtnListByMenuId(menuId));
    }

    /**
     * 获取按钮详细信息
     */
    @Operation(summary = "获取按钮详细信息", description = "根据ID获取按钮的详细信息")
    @GetMapping(value = "/{id}")
    public Result<SysBtnDTO> getInfo(@Parameter(description = "按钮ID", required = true) @PathVariable Long id) {
        return Result.ok(btnService.selectBtnById(id));
    }

    /**
     * 新增按钮
     */
    @Operation(summary = "新增按钮", description = "新增一个按钮")
    @PostMapping
    public Result<Void> add(@Valid @RequestBody SysBtnDTO btnDTO) {
        return btnService.insertBtn(btnDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 修改按钮
     */
    @Operation(summary = "修改按钮", description = "修改按钮信息")
    @PutMapping
    public Result<Void> edit(@Valid @RequestBody SysBtnDTO btnDTO) {
        return btnService.updateBtn(btnDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 删除按钮
     */
    @Operation(summary = "删除按钮", description = "根据ID列表删除按钮")
    @DeleteMapping("/{ids}")
    public Result<Void> remove(@Parameter(description = "按钮ID列表", required = true) @PathVariable List<Long> ids) {
        return btnService.deleteBtnByIds(ids) ? Result.ok() : Result.fail();
    }
}
