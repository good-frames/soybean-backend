package com.soybean.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.soybean.auth.domain.dto.LoginDTO;
import com.soybean.auth.domain.vo.LoginVO;
import com.soybean.auth.service.AuthService;
import com.soybean.common.core.exception.BusinessException;
import com.soybean.common.core.utils.PasswordUtil;
import com.soybean.common.core.utils.Result;
import com.soybean.common.security.model.LoginUser;
import com.soybean.common.security.util.SecurityUtil;
import com.soybean.common.security.enums.UserTypeEnum;
import com.soybean.user.api.clients.SysUserClient;
import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.po.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 认证服务实现
 *
 * @author soybean
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserClient sysUserClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 获取用户信息
        Result<SysUser> userResult = sysUserClient.getUserByUsername(loginDTO.getUsername());
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户不存在");
        }


        SysUser authInfo = userResult.getData();
        // 验证用户状态
        if (SysUserStatusEnum.DISABLE.equals(authInfo.getStatus())) {
            throw new BusinessException("账户已被禁用");
        }
        // 验证密码
        if (!PasswordUtil.verify(loginDTO.getPassword(), authInfo.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        // 转换为LoginUser
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(authInfo.getUserId().toString());
        loginUser.setUsername(authInfo.getUsername());
        loginUser.setNickname(authInfo.getNickname());
        loginUser.setUserType(UserTypeEnum.ADMIN);
        loginUser.setLoginTime(new Date());
        
        // 使用SecurityUtil进行登录
        SecurityUtil.login(loginUser);

        // 获取Token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 构建返回结果
        return new LoginVO(tokenInfo.getTokenValue(), tokenInfo.getTokenTimeout());
    }

    @Override
    public void logout(String tokenValue) {
        // 根据token值进行登出
        StpUtil.logoutByTokenValue(tokenValue);
    }

}
