package com.soybean.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soybean.common.core.exception.BusinessException;
import com.soybean.common.storage.dto.SysStorageConfigDTO;
import com.soybean.common.storage.handler.UploadFileHandler;
import com.soybean.common.storage.utils.FileUtils;
import com.soybean.upms.api.dto.SysFileUploadDTO;
import com.soybean.upms.api.po.SysStorageConfig;
import com.soybean.upms.api.vo.SysFileUploadVO;
import com.soybean.upms.service.ISysFileUploadService;
import com.soybean.upms.service.ISysStorageConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 文件上传服务实现类
 *
 * @author soybean
 * @since 2024-07-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileUploadServiceImpl implements ISysFileUploadService {

    private final ISysStorageConfigService storageConfigService;

    private final Map<String, UploadFileHandler> uploadFileHandlerMap;

    /**
     * 上传文件
     *
     * @param sysFileUploadDTO 文件上传信息
     * @return 文件上传结果
     */
    @Override
    public SysFileUploadVO uploadFile(SysFileUploadDTO sysFileUploadDTO) throws IOException {
        MultipartFile multipartFile = sysFileUploadDTO.getFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 查询启用的存储配置
        LambdaQueryWrapper<SysStorageConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysStorageConfig::getStatus, "1").last("LIMIT 1");
        SysStorageConfig storageConfig = storageConfigService.getOne(wrapper);

        if (storageConfig == null) {
            throw new BusinessException("未找到可用的存储配置");
        }

        // 转换为 DTO
        SysStorageConfigDTO configDTO = new SysStorageConfigDTO();
        configDTO.setType(storageConfig.getType());
        configDTO.setAccessKey(storageConfig.getAccessKey());
        configDTO.setAccessSecret(storageConfig.getAccessSecret());
        configDTO.setEndpoint(storageConfig.getEndpoint());
        configDTO.setBucket(storageConfig.getBucket());
        configDTO.setDir(storageConfig.getDir());

        File newFile = FileUtils.multipartFileToFile(multipartFile);
        String url = uploadFileHandlerMap.get("uploadFile" + storageConfig.getType())
                .uploadFile(configDTO, newFile, multipartFile.getContentType());

        // 组装返回结果
        SysFileUploadVO result = new SysFileUploadVO();
        result.setUrl(url);
        result.setFileName(multipartFile.getOriginalFilename());
        result.setFileSize(multipartFile.getSize());
        result.setContentType(multipartFile.getContentType());

        return result;
    }
}
