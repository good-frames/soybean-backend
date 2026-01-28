package com.soybean.common.openapi.config;

import com.soybean.common.openapi.properties.OpenApiProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(OpenApiProperties properties) {
        OpenAPI openAPI = new OpenAPI().info(
                new Info().title(properties.getTitle())
                        .version(properties.getVersion())
        );

        // 1. 自动加网关前缀（解决你 discovery locator 的核心问题）
        if (StringUtils.hasText(properties.getServicePrefix())) {
            openAPI.addServersItem(
                    new Server().url(properties.getServicePrefix())
            );
        }

        if (StringUtils.hasText(properties.getSecurityHeader())) {
            openAPI
                .addSecurityItem(
                    new SecurityRequirement()
                        .addList(properties.getSecurityHeader())
                )
                .components(
                    new Components()
                        .addSecuritySchemes(
                            properties.getSecurityHeader(),
                            new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(properties.getSecurityHeader())
                        )
                );
        }

        return openAPI;

//        // 自定义 security scheme 名称
//        final String securitySchemeName = "Authorization";
//
//        return new OpenAPI()
//            .info(new Info().title("UPMS API").version("v1"))
//            // 所有接口默认要求该 security
//            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//            .components(
//                new Components()
//                    .addSecuritySchemes(securitySchemeName,
//                        new SecurityScheme()
//                            .type(SecurityScheme.Type.APIKEY)  // APIKEY 类型
//                            .in(SecurityScheme.In.HEADER)      // Header 里传
//                            .name("Authorization")             // Sa-Token header 名
//                    )
//            );
    }
}
