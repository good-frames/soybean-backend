package com.soybean.user.validator;

import com.soybean.user.api.dto.SysUserDTO;
import com.soybean.user.api.validator.BaseUserValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 管理员用户验证器
 *
 * @author soybean
 */
@Component
public class SysUserValidator extends BaseUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SysUserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SysUserDTO adminUser = (SysUserDTO) target;

        // 验证用户名
        validateUsername(adminUser.getUsername(), errors);

        // 验证密码
//        validatePassword(adminUser.getPassword(), errors);

        // 验证昵称
        validateNickname(adminUser.getNickname(), errors);

        // 验证手机号
        validateRequiredPhone(adminUser.getPhone(), errors);

        // 验证邮箱
        validateEmail(adminUser.getEmail(), errors);

        // 验证状态
        validateStatus(adminUser.getStatus(), errors);

        // 验证性别
        validateGender(adminUser.getGender(), errors);
    }
}
