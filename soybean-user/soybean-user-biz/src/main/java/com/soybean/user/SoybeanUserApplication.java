package com.soybean.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SoybeanUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoybeanUserApplication.class, args);
    }

}
