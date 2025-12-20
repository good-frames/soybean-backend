package com.soybean.user.api.query;

import com.soybean.common.mybatis.query.PageQuery;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 管理员查询参数
 *
 * @author soybean
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserQuery extends PageQuery implements Serializable {
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态：0->禁用；1->启用
     */
    private AdminUserStatusEnum status;
}
