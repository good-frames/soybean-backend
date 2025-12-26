package com.soybean.common.security.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.util.SaResult;
import com.soybean.common.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    // 注册 Sa-Token 全局过滤器
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/favicon.ico")
                .setAuth(obj -> {
                    // 根据配置决定是否校验 Same-Token 身份凭证
                    if (securityProperties.isEnableSameToken()) {
                        // 校验 Same-Token 身份凭证     —— 以下两句代码可简化为：SaSameUtil.checkCurrentRequestToken();
                        String token = SaHolder.getRequest().getHeader(SaSameUtil.SAME_TOKEN);
                        SaSameUtil.checkToken(token);
                    }
                })
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                });
    }
}
