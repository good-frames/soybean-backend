package com.soybean.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soybean.upms.api.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
