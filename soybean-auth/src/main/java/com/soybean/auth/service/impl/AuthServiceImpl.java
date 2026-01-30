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
import com.soybean.upms.api.clients.SysMenuClient;
import com.soybean.upms.api.clients.SysRoleClient;
import com.soybean.upms.api.clients.SysUserClient;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.po.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    private final SysMenuClient sysMenuClient;
    private final SysRoleClient sysRoleClient;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 获取用户信息
        Result<SysUser> userResult = sysUserClient.getByUsername(loginDTO.getUsername());
        if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证用户状态
        if (StatusEnum.DISABLE.equals(userResult.getData().getStatus())) {
            throw new BusinessException("账户已被禁用");
        }
        // 验证密码
        if (!PasswordUtil.verify(loginDTO.getPassword(), userResult.getData().getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        // 获取用户权限
        Result<List<String>> permissionsResult = sysMenuClient.getCurrentUserPermissions(userResult.getData().getUserId());
        Result<List<String>> roleKeysResult = sysRoleClient.getRoleKeysByUserId(userResult.getData().getUserId());

        // 转换为LoginUser
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userResult.getData().getUserId());
        loginUser.setUsername(userResult.getData().getUsername());
        loginUser.setNickname(userResult.getData().getNickname());
        loginUser.setUserType(UserTypeEnum.ADMIN);
        loginUser.setLoginTime(new Date());
        loginUser.setPermissions(permissionsResult.getData());
        loginUser.setRoles(roleKeysResult.getData());
        
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

    @Override
    public boolean renewalToken(long timeout) {
        try {
            // 对当前 Token 的 timeout 值进行续期
            StpUtil.renewTimeout(timeout);
            return true;
        } catch (Exception e) {
            log.error("Token续期失败", e);
            return false;
        }
    }

}
