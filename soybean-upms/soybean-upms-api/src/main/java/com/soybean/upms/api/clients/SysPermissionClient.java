package com.soybean.upms.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(value = "soybean-upms")
public interface SysPermissionClient {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetMapping("/remote/permission/user")
    List<String> selectPermissionsByUserId(@RequestParam String userId);
}