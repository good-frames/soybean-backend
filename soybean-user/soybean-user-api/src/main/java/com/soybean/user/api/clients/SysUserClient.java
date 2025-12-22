package com.soybean.user.api.clients;

import com.soybean.common.core.utils.Result;
import com.soybean.user.api.vo.SysUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "soybean-user")
public interface SysUserClient {
    @GetMapping("/system/user/list")
    Result<List<SysUserVO>> getAllAdminUsers();
}
