package com.soybean.common.security.model;

import com.soybean.common.security.enums.UserTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息
 */
@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 用户角色列表
     */
    private List<String> roles;

    /**
     * 用户权限列表
     */
    private List<String> permissions;

    /**
     * 登录设备
     */
    private LoginDevice loginDevice;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraInfo;

    /**
     * 获取扩展信息
     */
    public Object getExtraInfo(String key) {
        if (extraInfo == null) {
            extraInfo = new HashMap<>();
        }
        return extraInfo.get(key);
    }

    /**
     * 设置扩展信息
     */
    public void setExtraInfo(String key, Object value) {
        if (extraInfo == null) {
            extraInfo = new HashMap<>();
        }
        extraInfo.put(key, value);
    }
}
