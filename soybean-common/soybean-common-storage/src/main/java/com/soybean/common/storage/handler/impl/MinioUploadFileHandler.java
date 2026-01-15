package com.soybean.common.storage.handler.impl;

import com.soybean.common.storage.entity.StorageConfig;
import com.soybean.common.storage.handler.AbstractUploadFileHandler;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * Minio上传文件处理器
 */
@Component("Minio")
public class MinioUploadFileHandler extends AbstractUploadFileHandler {

	@SneakyThrows
	@Override
	public String doUploadFile(StorageConfig storageConfig, File file) {
		String endpoint = storageConfig.getEndpoint();
		MinioClient minioClient = MinioClient.builder()
			.endpoint(endpoint)
			.credentials(storageConfig.getAccessKeyId(), storageConfig.getAccessKeySecret())
			.build();

		String bucketName = storageConfig.getBucket();
		String fileName = file.getName();
		// 获取后缀
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String dir = storageConfig.getDir();
		String uuidFileName = UUID.randomUUID() + "." + suffix;
		String objectName = (StringUtils.hasText(dir) ? (dir + "/") : "") + uuidFileName;
		PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			.bucket(bucketName)
			.object(objectName)
			.stream(new FileInputStream(file), file.length(), -1)
			.contentType(storageConfig.getContextType())
			.build();

		minioClient.putObject(putObjectArgs);
		return endpoint + "/" + bucketName + "/" + objectName;
	}

}
