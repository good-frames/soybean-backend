package com.soybean.common.storage.handler;


import com.soybean.common.storage.dto.SysStorageConfigDTO;
import com.soybean.common.storage.entity.StorageConfig;

import java.io.File;

/**
 * @author lijx
 */
public abstract class AbstractUploadFileHandler implements UploadFileHandler {

	@Override
	public String uploadFile(SysStorageConfigDTO sysStorageConfig, File file, String contextType) {
		StorageConfig storageConfig = validateRequest(sysStorageConfig, contextType);
		return doUploadFile(storageConfig, file);
	}

	public abstract String doUploadFile(StorageConfig storageConfig, File file);

	private StorageConfig validateRequest(SysStorageConfigDTO sysStorageConfig, String contextType) {
		StorageConfig storageConfig = new StorageConfig();
		storageConfig.setAccessKeyId(sysStorageConfig.getAccessKey());
		storageConfig.setAccessKeySecret(sysStorageConfig.getAccessSecret());
		storageConfig.setBucket(sysStorageConfig.getBucket());
		storageConfig.setEndpoint(sysStorageConfig.getEndpoint());
		storageConfig.setDir(sysStorageConfig.getDir());
		storageConfig.setContextType(contextType);
		return storageConfig;
	}

}
