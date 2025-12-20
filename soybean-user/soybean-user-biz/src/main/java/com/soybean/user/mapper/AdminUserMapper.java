package com.soybean.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soybean.user.api.po.AdminUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台管理员表 Mapper 接口
 * </p>
 *
 * @author dongdongjie
 * @since 2025-12-16
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

}
