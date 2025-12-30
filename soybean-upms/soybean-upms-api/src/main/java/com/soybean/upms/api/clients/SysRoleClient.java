package com.soybean.upms.api.clients;

import com.soybean.common.core.utils.Result;
import com.soybean.upms.api.vo.SysRoleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "soybean-upms", contextId = "SysRoleClient", path = "/upms/role")
public interface SysRoleClient {
    /**
     * 根据用户ID获取角色列表
     */
    @GetMapping("/user/{userId}/keys")
    Result<List<String>> getRoleKeysByUserId(@PathVariable String userId);
}
