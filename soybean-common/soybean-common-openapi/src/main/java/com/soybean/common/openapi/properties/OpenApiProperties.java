package com.soybean.common.openapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "soybean.openapi")
public class OpenApiProperties {
    private String servicePrefix; // 网关前缀，例如 /soybean-upms
    private String title;         // 文档标题
    private String version;     // 文档版本
    private String securityHeader; // Sa-Token header，例如 X-Token
}
