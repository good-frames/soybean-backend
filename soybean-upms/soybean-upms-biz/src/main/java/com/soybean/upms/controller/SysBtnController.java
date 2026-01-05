package com.soybean.upms.controller;
import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.dto.SysBtnDTO;
import com.soybean.upms.service.ISysBtnService;
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
@RestController
@RequestMapping("/btn")
@RequiredArgsConstructor
public class SysBtnController {

    private final ISysBtnService btnService;

    /**
     * 查询按钮列表
     */
    @GetMapping("/list")
    public Result<List<SysBtnDTO>> list(SysBtnDTO btnDTO) {
        return Result.ok(btnService.selectBtnList(btnDTO));
    }

    /**
     * 根据菜单ID查询按钮列表
     */
    @GetMapping("/listByMenuId/{menuId}")
    public Result<List<SysBtnDTO>> listByMenuId(@PathVariable Long menuId) {
        return Result.ok(btnService.selectBtnListByMenuId(menuId));
    }

    /**
     * 获取按钮详细信息
     */
    @GetMapping(value = "/{id}")
    public Result<SysBtnDTO> getInfo(@PathVariable Long id) {
        return Result.ok(btnService.selectBtnById(id));
    }

    /**
     * 新增按钮
     */
    @PostMapping
    public Result<Void> add(@Valid @RequestBody SysBtnDTO btnDTO) {
        return btnService.insertBtn(btnDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 修改按钮
     */
    @PutMapping
    public Result<Void> edit(@Valid @RequestBody SysBtnDTO btnDTO) {
        return btnService.updateBtn(btnDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 删除按钮
     */
    @DeleteMapping("/{ids}")
    public Result<Void> remove(@PathVariable List<Long> ids) {
        return btnService.deleteBtnByIds(ids) ? Result.ok() : Result.fail();
    }
}
