package com.soybean.upms.api.clients;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "soybean-upms", contextId = "SysUserClient", path = "/user")
public interface SysUserClient {

    @GetMapping("/getByUsername")
    Result<SysUser> getByUsername(@RequestParam("username") String username);
}