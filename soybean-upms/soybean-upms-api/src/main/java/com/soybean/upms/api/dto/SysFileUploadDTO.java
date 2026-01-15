package com.soybean.upms.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传数据传输对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "文件上传数据传输对象")
public class SysFileUploadDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "文件不能为空")
    @Schema(description = "文件")
    private MultipartFile file;

    @Schema(description = "文件类型")
    private String contentType;
}
