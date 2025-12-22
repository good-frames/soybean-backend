package com.soybean.common.security.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.soybean.common.security.constant.SecurityConstant;
import com.soybean.common.security.enums.UserTypeEnum;
import com.soybean.common.security.model.LoginResult;
import com.soybean.common.security.model.LoginUser;
import com.soybean.common.security.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 认证服务实现类（示例）
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResult login(String username, String password, UserTypeEnum userType, boolean remember) {
        // TODO: 实际项目中应该从数据库验证用户信息
        // 这里仅作示例，实际使用时需要替换为真实的业务逻辑

        // 模拟用户验证
        if (!"admin".equals(username) || !"123456".equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 创建登录用户对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId("1");
        loginUser.setUsername(username);
        loginUser.setNickname("管理员");
        loginUser.setUserType(userType);
        loginUser.setLoginTime(new Date());
        // 设置过期时间
        long timeout = StpUtil.getTokenTimeout();
        if (timeout > 0) {
            loginUser.setExpireTime(new Date(System.currentTimeMillis() + timeout * 1000));
        }

        // 登录
        StpUtil.login(loginUser.getUserId(), remember);

        // 将用户信息存储到Session中
        StpUtil.getSession().set(SecurityConstant.USER_SESSION_KEY, loginUser);

        // 创建登录结果
        LoginResult result = new LoginResult();
        result.setToken(StpUtil.getTokenValue());
        result.setTokenType("Bearer");
        result.setExpiresIn(timeout);
        result.setUserInfo(loginUser);
        result.setIsFirstLogin(false);
        result.setNeedChangePassword(false);

        log.info("用户 {} 登录成功", username);
        return result;
    }

    @Override
    public void logout() {
        // 获取当前用户ID
        String userId = StpUtil.getLoginId().toString();

        // 退出登录
        StpUtil.logout();

        log.info("用户 {} 退出登录", userId);
    }

    @Override
    public LoginUser getCurrentUser() {
        try {
            if (!StpUtil.isLogin()) {
                return null;
            }

            // 从Session中获取用户信息
            return StpUtil.getSession().getModel(SecurityConstant.USER_SESSION_KEY, LoginUser.class);
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            return null;
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        try {
            return StpUtil.hasPermission(permission);
        } catch (Exception e) {
            log.error("检查权限失败: {}", permission, e);
            return false;
        }
    }

    @Override
    public boolean hasRole(String role) {
        try {
            return StpUtil.hasRole(role);
        } catch (Exception e) {
            log.error("检查角色失败: {}", role, e);
            return false;
        }
    }

    @Override
    public String refreshToken() {
        // 刷新token
        StpUtil.renewTimeout(StpUtil.getTokenTimeout());
        return StpUtil.getTokenValue();
    }

    @Override
    public void kickout(String userId) {
        // 根据用户ID踢人下线
        StpUtil.kickout(userId);
        log.info("用户 {} 已被踢下线", userId);
    }
}
