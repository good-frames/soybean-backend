package com.soybean.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 放行URL配置
 *
 * @author soybean
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.permit-all")
public class PermitAllUrlProperties {

    /**
     * 放行URL
     */
    private List<String> urls = new ArrayList<>();
}
