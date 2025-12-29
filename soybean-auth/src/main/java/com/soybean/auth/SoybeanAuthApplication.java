package com.soybean.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.soybean", defaultConfiguration = com.soybean.common.security.config.FeignConfig.class)
@SpringBootApplication
public class SoybeanAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoybeanAuthApplication.class, args);
    }

}
