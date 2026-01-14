package com.soybean.upms.api.validator;

import com.soybean.common.core.enums.BaseEnum;
import com.soybean.common.core.validator.CommonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

/**
 * 基础用户验证器，包含通用的用户验证方法
 *
 * @author soybean
 */
@Slf4j
public class BaseUserValidator extends CommonValidator {


    /**
     * 验证用户名
     */
    protected void validateUsername(String userName, String fieldName, Boolean required, Errors errors) {
        // 验证必填项
        if (required && StringUtils.isBlank(userName)) {
            errors.rejectValue(fieldName, "userName.blank", "用户名不能为空");
            return;
        }

        // 如果用户名不为空，验证格式
        if (StringUtils.isNotBlank(userName) && (userName.length() < 3 || userName.length() > 20)) {
            errors.rejectValue(fieldName, "userName.length", "用户名长度必须在3-20个字符之间");
        }
    }

    /**
     * 验证密码
     */
    protected void validatePassword(String password, String fieldName, Boolean required, Errors errors) {
        // 验证必填项
        if (required && StringUtils.isBlank(password)) {
            errors.rejectValue(fieldName, "password.blank", "密码不能为空");
            return;
        }

        // 如果用户名不为空，验证格式
        if (StringUtils.isNotBlank(password) && (password.length() < 6 || password.length() > 20)) {
            errors.rejectValue(fieldName, "password.length", "密码长度必须在6-20个字符之间");
        }
    }

    /**
     * 验证昵称
     */
    protected void validateNickname(String nickName, String fieldName, Boolean required, Errors errors) {
        // 验证必填项
        if (required && StringUtils.isBlank(nickName)) {
            errors.rejectValue(fieldName, "nickName.blank", "昵称不能为空");
            return;
        }

        // 如果用户名不为空，验证格式
        if (StringUtils.isNotBlank(nickName) && nickName.length() > 30) {
            errors.rejectValue(fieldName, "nickName.length", "昵称长度不能超过30个字符");
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

    /**
     * 验证性别
     */
    protected void validateGender(Object gender, Errors errors) {
        // 性别可以为空，如果不为空则必须是BaseEnum的实例
        if (gender != null && !(gender instanceof BaseEnum)) {
            errors.rejectValue("gender", "gender.type", "性别必须是1(男)、0(女)或2(未知)");
        }
    }

    /**
     * 验证删除标记
     */
    protected void validateDelFlag(Object delFlag, Errors errors) {
        // 删除标记可以为空，如果不为空则必须是BaseEnum的实例
        if (delFlag != null && !(delFlag instanceof BaseEnum)) {
            errors.rejectValue("delFlag", "delFlag.type", "删除标记必须是0(正常)或2(删除)");
        }
    }
    
//    /**
//     * 验证管理员修改密码请求
//     */
//    public void validateAdminPasswordUpdate(AdminPasswordUpdateDTO request, Errors errors) {
//        // 验证用户ID
//        validateRequiredId(request.getUserId(), "userId", errors);
//
//        // 验证新密码
//        validatePassword(request.getNewPassword(), "newPassword", true, errors);
//    }
}
