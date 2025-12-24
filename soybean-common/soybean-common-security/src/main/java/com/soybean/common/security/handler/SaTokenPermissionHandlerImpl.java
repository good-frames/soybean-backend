package com.soybean.common.security.handler;

import cn.dev33.satoken.stp.StpInterface;
import com.soybean.common.security.util.SecurityUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sa-Token权限接口实现
 */
@Component
public class SaTokenPermissionHandlerImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        return SecurityUtil.getCurrentUser().getPermissions();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return SecurityUtil.getCurrentUser().getRoles();
    }
}
