package com.soybean.common.core.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.soybean.common.core.annotation.Desensitization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 数据脱敏序列化器
 *
 * @author soybean
 * @date 2023-05-31
 */
public class DesensitizationJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private Class<? extends AbstractDesensitization> desensitizationClass;

    public DesensitizationJsonSerializer() {
    }

    public DesensitizationJsonSerializer(Class<? extends AbstractDesensitization> desensitizationClass) {
        this.desensitizationClass = desensitizationClass;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (desensitizationClass != null && value != null) {
            try {
                AbstractDesensitization desensitization = desensitizationClass.getDeclaredConstructor().newInstance();
                gen.writeString(desensitization.desensitize(value));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                gen.writeString(value);
            }
        } else {
            gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Desensitization annotation = property.getAnnotation(Desensitization.class);
        if (Objects.nonNull(annotation)) {
            return new DesensitizationJsonSerializer(annotation.value());
        }
        return this;
    }
}
