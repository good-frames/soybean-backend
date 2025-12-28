package com.soybean.user.remote;

import com.soybean.user.api.po.SysUser;
import com.soybean.user.service.ISysUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Data
@RestController
@RequestMapping("/remote/user/admin")
public class SysUserRemote {
    private final ISysUserService sysUserService;

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/getByUsername")
    public SysUser getByUsername(@RequestParam("username") String username) {
        try {
            return sysUserService.getUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }
}
