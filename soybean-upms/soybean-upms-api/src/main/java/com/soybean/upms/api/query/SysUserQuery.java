package com.soybean.upms.api.query;

import com.soybean.common.mybatis.query.PageQuery;
import com.soybean.upms.api.enums.SysUserGenderEnum;
import com.soybean.common.core.enums.StatusEnum;
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
public class SysUserQuery extends PageQuery implements Serializable {
    
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

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
    private StatusEnum status;
    
    /**
     * 性别：1->男；0->女；2->未知
     */
    private SysUserGenderEnum gender;
}
