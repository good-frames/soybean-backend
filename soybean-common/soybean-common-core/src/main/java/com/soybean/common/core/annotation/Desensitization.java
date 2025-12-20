package com.soybean.common.core.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.soybean.common.core.desensitization.AbstractDesensitization;
import com.soybean.common.core.desensitization.DesensitizationJsonSerializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据脱敏注解
 *
 * @author soybean
 * @date 2023-05-31
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationJsonSerializer.class)
public @interface Desensitization {

    Class<? extends AbstractDesensitization> value();
}
