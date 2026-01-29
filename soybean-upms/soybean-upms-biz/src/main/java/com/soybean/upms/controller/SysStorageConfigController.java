package com.soybean.upms.controller;

import cn.hutool.core.bean.BeanUtil;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysStorageConfigDTO;
import com.soybean.upms.api.query.SysStorageConfigQuery;
import com.soybean.upms.api.vo.SysStorageConfigVO;
import com.soybean.upms.service.ISysStorageConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统文件存储配置控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@Tag(name = "存储配置管理", description = "系统文件存储配置的增删改查")
@RestController
@RequestMapping("/storageConfig")
@RequiredArgsConstructor
public class SysStorageConfigController {

    private final ISysStorageConfigService storageConfigService;

    /**
     * 新增存储配置
     */
    @Operation(summary = "新增存储配置", description = "创建新的存储配置")
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysStorageConfigDTO configDTO) {
        return storageConfigService.insertStorageConfig(configDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 删除存储配置
     */
    @Operation(summary = "删除存储配置", description = "批量删除存储配置")
    @DeleteMapping
    public Result<Void> remove(@RequestBody List<Long> ids) {
        try {
            return storageConfigService.deleteStorageConfigByIds(ids) ? Result.ok() : Result.fail();
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改保存存储配置
     */
    @Operation(summary = "修改存储配置", description = "修改存储配置信息")
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysStorageConfigDTO configDTO) {
        if (configDTO.getId() == null) {
            return Result.fail("修改存储配置失败，配置ID不能为空");
        }

        if (storageConfigService.updateStorageConfig(configDTO)) {
            return Result.ok();
        }
        return Result.fail("修改存储配置失败，请联系管理员");
    }

    /**
     * 获取存储配置分页列表
     */
    @Operation(summary = "分页查询存储配置", description = "根据条件分页查询存储配置列表")
    @GetMapping("/page")
    public Result<PageDTO<SysStorageConfigVO>> page(SysStorageConfigQuery query) {
        PageDTO<SysStorageConfigVO> result = storageConfigService.getStorageConfigPage(query);
        return Result.ok(result);
    }

    /**
     * 根据配置编号获取详细信息
     */
    @Operation(summary = "获取存储配置详情", description = "根据配置ID获取存储配置详细信息")
    @GetMapping(value = "/{id}")
    public Result<SysStorageConfigVO> getInfo(@Parameter(description = "配置ID") @PathVariable Long id) {
        return Result.ok(BeanUtil.copyProperties(storageConfigService.getById(id), SysStorageConfigVO.class));
    }
}
