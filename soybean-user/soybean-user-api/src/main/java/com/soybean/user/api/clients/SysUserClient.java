package com.soybean.user.api.clients;

import com.soybean.common.core.utils.Result;
import com.soybean.user.api.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "soybean-user", contextId = "SysUserClient", path = "/systemManage/user")
public interface SysUserClient {

    @GetMapping("/getByUsername")
    Result<SysUser> getByUsername(@RequestParam("username") String username);
}
