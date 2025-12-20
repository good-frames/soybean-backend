package com.soybean.user.api.validator;

import com.soybean.common.core.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

/**
 * 基础用户验证器，包含通用的用户验证方法
 *
 * @author soybean
 */
@Slf4j
public class BaseUserValidator {

    /**
     * 验证用户名
     */
    protected void validateUsername(String username, Errors errors) {
        if (StringUtils.isBlank(username)) {
            errors.rejectValue("username", "username.blank", "用户名不能为空");
        } else if (username.length() < 3 || username.length() > 20) {
            errors.rejectValue("username", "username.length", "用户名长度必须在3-20个字符之间");
        }
    }

    /**
     * 验证密码
     */
    protected void validatePassword(String password, Errors errors) {
        if (StringUtils.isNotBlank(password) && (password.length() < 6 || password.length() > 20)) {
            errors.rejectValue("password", "password.length", "密码长度必须在6-20个字符之间");
        }
    }

    /**
     * 验证昵称
     */
    protected void validateNickname(String nickname, Errors errors) {
        if (StringUtils.isNotBlank(nickname) && nickname.length() > 30) {
            errors.rejectValue("nickname", "nickname.length", "昵称长度不能超过30个字符");
        }
    }

    /**
     * 验证手机号
     */
    protected void validatePhone(String phone, Errors errors) {
        if (StringUtils.isNotBlank(phone) && !phone.matches("^1[3-9]\\d{9}$")) {
            errors.rejectValue("phone", "phone.format", "手机号格式不正确");
        }
    }

    /**
     * 验证必填手机号
     */
    protected void validateRequiredPhone(String phone, Errors errors) {
        if (StringUtils.isBlank(phone)) {
            errors.rejectValue("phone", "phone.blank", "手机号不能为空");
        } else if (!phone.matches("^1[3-9]\\d{9}$")) {
            errors.rejectValue("phone", "phone.format", "手机号格式不正确");
        }
    }

    /**
     * 验证邮箱
     */
    protected void validateEmail(String email, Errors errors) {
        if (StringUtils.isNotBlank(email) && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.rejectValue("email", "email.format", "邮箱格式不正确");
        }
    }

    /**
     * 验证状态
     */
    protected void validateStatus(Object status, Errors errors) {
        // 状态可以为空，如果不为空则必须是BaseEnum的实例
        if (status != null && !(status instanceof BaseEnum)) {
            errors.rejectValue("status", "status.type", "状态必须是0或1");
        }
    }
}
