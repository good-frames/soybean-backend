package com.soybean.common.storage.handler;

import com.soybean.common.storage.dto.SysStorageConfigDTO;

import java.io.File;

public interface UploadFileHandler {
    String uploadFile(SysStorageConfigDTO sysStorageConfig, File file, String contextType);
}
