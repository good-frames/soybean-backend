package com.soybean.common.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.soybean.common.security.constant.SecurityConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token拦截器，校验规则为StpUtil::checkLogin，即所有请求都需要登录
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 根据路由匹配不同的校验规则
            SaRouter.match("/**")
                .notMatch(SecurityConstant.EXCLUDE_PATH_PATTERNS)
                .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}
