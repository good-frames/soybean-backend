
package com.soybean.common.security.config;

import com.soybean.common.security.interceptor.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }
}
