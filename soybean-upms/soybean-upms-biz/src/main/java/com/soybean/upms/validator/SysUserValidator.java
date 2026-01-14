package com.soybean.upms.validator;

import com.soybean.upms.api.dto.PasswordUpdateDTO;
import com.soybean.upms.api.dto.SysUserDTO;
import com.soybean.upms.api.validator.BaseUserValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 管理员用户验证器
 *
 * @author soybean
 */
@Component
public class SysUserValidator extends BaseUserValidator {

    public void addValidate(Object target, Errors errors) {
        SysUserDTO adminUser = (SysUserDTO) target;

        // 验证用户名
        extracted(errors, adminUser);
    }

    public void updateValidate(Object target, Errors errors) {
        SysUserDTO adminUser = (SysUserDTO) target;

        // 验证ID
        validateId(adminUser.getId(), "id", true, errors);

        extracted(errors, adminUser);
    }

    public void updatePasswordValidate(Object target, Errors errors) {
        PasswordUpdateDTO passwordUpdateDTO = (PasswordUpdateDTO) target;
        // 验证ID
        validateId(passwordUpdateDTO.getId(), "id", true, errors);
        // 验证密码
        validatePassword(passwordUpdateDTO.getNewPassword(), "newPassword", true, errors);
    }


    private void extracted(Errors errors, SysUserDTO adminUser) {
        // 验证用户名
        validateUsername(adminUser.getUserName(), "userName", true, errors);

        // 验证昵称
        validateNickname(adminUser.getNickName(), "nickName", true, errors);

        // 验证手机号
        validatePhone(adminUser.getPhone(), "phone", true, errors);

        // 验证邮箱
        validateEmail(adminUser.getEmail(), "email", false, errors);

        // 验证状态
        validateStatus(adminUser.getStatus(), errors);

        // 验证性别
        validateGender(adminUser.getGender(), errors);
    }
}
