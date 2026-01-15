package com.soybean.upms.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.dto.SysFileUploadDTO;
import com.soybean.upms.api.vo.SysFileUploadVO;
import com.soybean.upms.service.ISysFileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 文件上传控制器
 *
 * @author soybean
 * @since 2024-07-07
 */
@RestController
@RequestMapping("/systemManage/file")
@RequiredArgsConstructor
@Tag(name = "文件上传管理", description = "文件上传相关接口")
public class SysFileUploadController {

    private final ISysFileUploadService fileUploadService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件到云存储")
    public Result<SysFileUploadVO> upload(@Validated @ModelAttribute SysFileUploadDTO sysFileUploadDTO) throws IOException {
        SysFileUploadVO result = fileUploadService.uploadFile(sysFileUploadDTO);
        return Result.ok(result);
    }
}
