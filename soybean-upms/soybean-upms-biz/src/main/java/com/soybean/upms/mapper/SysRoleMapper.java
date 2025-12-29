
package com.soybean.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soybean.upms.api.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 Mapper 接口
 *
 * @author soybean
 * @since 2024-07-07
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUserId(@Param("userId") String userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<SysRole> selectRoleAll();
}
