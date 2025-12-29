
package com.soybean.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soybean.upms.api.po.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表 Mapper 接口
 *
 * @author soybean
 * @since 2024-07-07
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID获取用户所拥有的权限集合
     *
     * @param userId 用户ID
     * @return 权限集合
     */
    Set<String> selectPermissionsByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限集合
     */
    Set<String> selectPermsByRoleId(@Param("roleId") Long roleId);
}
