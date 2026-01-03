package com.soybean.common.core.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

/**
 * 通用验证器，包含通用的验证方法
 *
 * @author soybean
 */
@Slf4j
public class CommonValidator {

    /**
     * 验证手机号
     * @param phone 手机号
     * @param fieldName 字段名，默认为"phone"
     * @param required 是否必填
     * @param errors 错误对象
     */
    protected void validatePhone(String phone, String fieldName, boolean required, Errors errors) {
        // 如果手机号为空且不是必填项，则不进行验证
        if (!required && StringUtils.isBlank(phone)) {
            return;
        }
        
        // 验证必填项
        if (required && StringUtils.isBlank(phone)) {
            errors.rejectValue(fieldName, "phone.blank", "手机号不能为空");
            return;
        }
        
        // 如果手机号不为空，验证格式
        if (StringUtils.isNotBlank(phone) && !phone.matches("^1[3-9]\\d{9}$")) {
            errors.rejectValue(fieldName, "phone.format", "手机号格式不正确");
        }
    }

    /**
     * 验证邮箱
     * @param email 邮箱
     * @param fieldName 字段名，默认为"email"
     * @param required 是否必填
     * @param errors 错误对象
     */
    protected void validateEmail(String email, String fieldName, boolean required, Errors errors) {
        // 如果邮箱为空且不是必填项，则不进行验证
        if (!required && StringUtils.isBlank(email)) {
            return;
        }
        
        // 验证必填项
        if (required && StringUtils.isBlank(email)) {
            errors.rejectValue(fieldName, "email.blank", "邮箱不能为空");
            return;
        }
        
        // 如果邮箱不为空，验证格式
        if (StringUtils.isNotBlank(email) && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.rejectValue(fieldName, "email.format", "邮箱格式不正确");
        }
    }

    /**
     * 验证ID
     * @param id ID
     * @param fieldName 字段名，默认为"id"
     * @param required 是否必填
     * @param errors 错误对象
     */
    protected void validateId(String id, String fieldName, boolean required, Errors errors) {
        // 如果ID为空且不是必填项，则不进行验证
        if (!required && StringUtils.isBlank(id)) {
            return;
        }
        
        // 验证必填项
        if (required && StringUtils.isBlank(id)) {
            errors.rejectValue(fieldName, "id.blank", "ID不能为空");
        }
    }

}
