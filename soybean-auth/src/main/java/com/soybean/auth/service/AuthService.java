package com.soybean.auth.service;

import com.soybean.auth.domain.dto.LoginDTO;
import com.soybean.auth.domain.vo.LoginVO;

/**
 * 认证服务接口
 *
 * @author soybean
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求
     * @return 登录响应
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户登出
     *
     * @param tokenValue token值
     */
    void logout(String tokenValue);

    /**
     * 续期当前Token
     *
     * @param timeout 续期时长（单位：秒）
     * @return 是否成功
     */
    boolean renewalToken(long timeout);
}
