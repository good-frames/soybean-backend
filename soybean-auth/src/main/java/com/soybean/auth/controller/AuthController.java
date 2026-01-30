package com.soybean.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.soybean.auth.domain.dto.LoginDTO;
import com.soybean.auth.domain.vo.LoginVO;
import com.soybean.auth.service.AuthService;
import com.soybean.common.core.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author soybean
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "提供用户登录、登出等认证相关接口")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        log.info("用户登录，用户名：{}", loginDTO.getUsername());
        LoginVO loginVO = authService.login(loginDTO);
        return Result.ok(loginVO);
    }

    @Operation(summary = "用户登出", description = "用户登出接口")
    @PostMapping("/logout")
    public Result<Void> logout() {
        String tokenValue = StpUtil.getTokenValue();
        authService.logout(tokenValue);
        return Result.ok();
    }

    @Operation(summary = "检查是否登录", description = "检查是否登录接口")
    @GetMapping("/check-login")
    public Result<Boolean> checkLogin() {
        boolean isLogin = StpUtil.isLogin();
        return Result.ok(isLogin);
    }

    @Operation(summary = "获取当前登录用户ID", description = "获取当前登录用户ID接口")
    @GetMapping("/get-login-id")
    public Result<Object> getLoginId() {
        Object loginId = StpUtil.getLoginId();
        return Result.ok(loginId);
    }

    @Operation(summary = "续期当前Token", description = "对当前Token进行续期")
    @PostMapping("/renewalToken")
    public Result<Boolean> renewalToken(@RequestParam long timeout) {
        boolean result = authService.renewalToken(timeout);
        return Result.ok(result);
    }
}
