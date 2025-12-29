package com.soybean.upms.remote;

import com.soybean.common.core.utils.Result;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.upms.service.ISysMenuService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@Data
@RestController
@RequestMapping("/remote")
public class SysPermissionRemote {

    private final ISysMenuService menuService;

    /**
     * 获取当前登录用户所拥有的权限集合
     */
    @GetMapping("/permission/user")
    public List<String> getCurrentUserPermissions(String userId) {
        return menuService.selectPermissionsByUserId(userId);
    }
}
