
package com.soybean.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安全配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "soybean.security")
public class SecurityProperties {

    /**
     * 是否启用内部服务Same-Token验证
     */
    private boolean enableSameToken = true;
}
