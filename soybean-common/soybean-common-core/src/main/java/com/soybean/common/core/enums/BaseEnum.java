
package com.soybean.common.core.enums;

/**
 * 基础枚举接口，所有枚举类都应实现此接口
 *
 * @author soybean
 */
public interface BaseEnum<T> {

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取枚举描述
     *
     * @return 枚举描述
     */
    String getDesc();
}
