package com.soybean.user.api.clients;

import com.soybean.common.core.utils.Result;
import com.soybean.user.api.po.SysUser;
import com.soybean.user.api.vo.SysUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "soybean-user")
public interface SysUserClient {

    @GetMapping("/user/admin/getByUsername")
    Result<SysUser> getUserByUsername(@RequestParam("username") String username);
}
