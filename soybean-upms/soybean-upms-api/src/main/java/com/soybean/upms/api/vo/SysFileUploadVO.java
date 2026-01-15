package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传结果视图对象
 *
 * @author soybean
 * @since 2024-07-07
 */
@Data
@Schema(description = "文件上传结果视图对象")
public class SysFileUploadVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文件URL")
    private String url;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件类型")
    private String contentType;
}
