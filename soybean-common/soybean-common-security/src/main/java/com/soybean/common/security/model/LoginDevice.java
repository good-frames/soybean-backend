package com.soybean.common.security.model;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录设备信息
 */
@Data
public class LoginDevice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 浏览器版本
     */
    private String browserVersion;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 地理位置
     */
    private String location;
}
