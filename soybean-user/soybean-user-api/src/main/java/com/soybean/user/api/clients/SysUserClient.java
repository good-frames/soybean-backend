package com.soybean.user.api.clients;

import com.soybean.user.api.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "soybean-user")
public interface SysUserClient {

    @GetMapping("/remote/user/admin/getByUsername")
    SysUser getUserByUsername(@RequestParam("username") String username);
}
