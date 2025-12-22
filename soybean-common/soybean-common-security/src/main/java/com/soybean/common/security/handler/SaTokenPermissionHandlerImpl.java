package com.soybean.common.security.handler;

import cn.dev33.satoken.stp.StpInterface;
import com.soybean.common.security.model.LoginUser;
import com.soybean.common.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Sa-Token权限接口实现
 */
@Component
public class SaTokenPermissionHandlerImpl implements StpInterface {

    @Autowired
    private AuthService authService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        try {
            LoginUser loginUser = authService.getCurrentUser();
            return loginUser != null ? loginUser.getPermissions() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        try {
            LoginUser loginUser = authService.getCurrentUser();
            return loginUser != null ? loginUser.getRoles() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
