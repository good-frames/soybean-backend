package com.soybean.common.security.service;

import com.soybean.common.security.enums.UserTypeEnum;
import com.soybean.common.security.model.LoginResult;
import com.soybean.common.security.model.LoginUser;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param userType 用户类型
     * @param remember 是否记住登录
     * @return 登录结果，包含token等信息
     */
    LoginResult login(String username, String password, UserTypeEnum userType, boolean remember);

    /**
     * 用户退出登录
     */
    void logout();

    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    LoginUser getCurrentUser();

    /**
     * 检查用户是否有指定权限
     * @param permission 权限标识
     * @return 是否有权限
     */
    boolean hasPermission(String permission);

    /**
     * 检查用户是否有指定角色
     * @param role 角色标识
     * @return 是否有角色
     */
    boolean hasRole(String role);

    /**
     * 刷新token
     * @return 新的token
     */
    String refreshToken();

    /**
     * 根据用户ID踢人下线
     * @param userId 用户ID
     */
    void kickout(String userId);
}
