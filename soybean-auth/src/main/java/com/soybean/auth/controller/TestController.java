package com.soybean.auth.controller;

import com.soybean.common.core.utils.Result;
import com.soybean.user.api.clients.SysUserClient;
import com.soybean.user.api.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试微服务调用控制器
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserClient sysUserClient;

    /**
     * 测试调用用户服务获取管理员列表
     */
    @GetMapping("/admin-users")
    public Result<List<SysUserVO>> getAdminUsers() {
        return sysUserClient.getAllAdminUsers();
    }
}
