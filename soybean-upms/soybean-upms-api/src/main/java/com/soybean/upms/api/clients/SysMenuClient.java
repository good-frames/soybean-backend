package com.soybean.upms.api.clients;

import com.soybean.common.core.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "soybean-upms", contextId = "SysMenuClient", path = "/systemManage/menu")
public interface SysMenuClient {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @GetMapping("/permission/user/{userId}")
    Result<List<String>> getCurrentUserPermissions(@PathVariable String userId);
}
