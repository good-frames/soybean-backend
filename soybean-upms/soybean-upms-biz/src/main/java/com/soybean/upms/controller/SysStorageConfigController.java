package com.soybean.upms.controller;

import cn.hutool.core.bean.BeanUtil;
import com.soybean.common.core.utils.Result;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysStorageConfigDTO;
import com.soybean.upms.api.query.SysStorageConfigQuery;
import com.soybean.upms.api.vo.SysStorageConfigVO;
import com.soybean.upms.service.ISysStorageConfigService;
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
@RestController
@RequestMapping("/systemManage/storageConfig")
@RequiredArgsConstructor
public class SysStorageConfigController {

    private final ISysStorageConfigService storageConfigService;

    /**
     * 新增存储配置
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysStorageConfigDTO configDTO) {
        return storageConfigService.insertStorageConfig(configDTO) ? Result.ok() : Result.fail();
    }

    /**
     * 删除存储配置
     */
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
    @GetMapping("/page")
    public Result<PageDTO<SysStorageConfigVO>> page(SysStorageConfigQuery query) {
        PageDTO<SysStorageConfigVO> result = storageConfigService.getStorageConfigPage(query);
        return Result.ok(result);
    }

    /**
     * 根据配置编号获取详细信息
     */
    @GetMapping(value = "/{id}")
    public Result<SysStorageConfigVO> getInfo(@PathVariable Long id) {
        return Result.ok(BeanUtil.copyProperties(storageConfigService.getById(id), SysStorageConfigVO.class));
    }
}
