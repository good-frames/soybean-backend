package com.soybean.upms.service;

import com.soybean.upms.api.dto.SysFileUploadDTO;
import com.soybean.upms.api.vo.SysFileUploadVO;

import java.io.IOException;

/**
 * 文件上传服务接口
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysFileUploadService {

    /**
     * 上传文件
     *
     * @param sysFileUploadDTO 文件上传信息
     * @return 文件上传结果
     */
    SysFileUploadVO uploadFile(SysFileUploadDTO sysFileUploadDTO) throws IOException;
}
