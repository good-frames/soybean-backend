package com.soybean.common.security.util;

import cn.dev33.satoken.stp.StpUtil;
import com.soybean.common.security.constant.SecurityConstant;
import com.soybean.common.security.model.LoginUser;

/**
 * 安全工具类
 */
public class SecurityUtil {

    /**
     * 获取当前登录用户ID
     */
    public static String getUserId() {
        return StpUtil.getLoginId().toString();
    }

    /**
     * 获取当前登录用户
     */
    public static LoginUser getCurrentUser() {
        return StpUtil.getSession().getModel(SecurityConstant.USER_SESSION_KEY, LoginUser.class);
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 检查用户是否有指定权限
     */
    public static boolean hasPermission(String permission) {
        return StpUtil.hasPermission(permission);
    }

    /**
     * 检查用户是否有指定角色
     */
    public static boolean hasRole(String role) {
        return StpUtil.hasRole(role);
    }

    /**
     * 获取当前登录用户的角色列表
     */
    public static java.util.List<String> getRoles() {
        return StpUtil.getRoleList();
    }

    /**
     * 获取当前登录用户的权限列表
     */
    public static java.util.List<String> getPermissions() {
        return StpUtil.getPermissionList();
    }

    /**
     * 获取token值
     */
    public static String getTokenValue() {
        return StpUtil.getTokenValue();
    }

    /**
     * 获取token名称
     */
    public static String getTokenName() {
        return StpUtil.getTokenName();
    }
}
