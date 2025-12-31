
package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.po.SysMenu;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.api.query.SysMenuTreeQuery;
import com.soybean.upms.api.vo.RouteTreeVO;
import com.soybean.upms.api.vo.SysMenuVO;
import com.soybean.upms.api.vo.MenuTreeVO;

import java.util.List;

/**
 * 菜单权限表 服务类
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuVO> selectMenuList(String userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menuQuery 菜单查询条件
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuVO> selectMenuList(SysMenuQuery menuQuery, String userId);

    /**
     * 根据用户ID获取用户所拥有的权限集合
     *
     * @param userId 用户ID
     * @return 权限集合
     */
    List<String> selectPermissionsByUserId(String userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 根据角色ID数组查询菜单列表（扁平化）
     *
     * @param roleIds 角色ID数组
     * @return 菜单列表
     */
    List<SysMenuVO> selectMenuFlatListByRoleIds(Long[] roleIds);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<SysMenuVO> buildMenuTree(List<SysMenuVO> menus);

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<SysMenuVO> buildMenuTreeSelect(List<SysMenuVO> menus);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenuVO selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean checkMenuExistRole(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean insertMenu(SysMenuDTO menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean updateMenu(SysMenuDTO menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenuDTO menu);

    /**
     * 构建前端路由菜单树
     *
     * @param userId 用户ID
     * @return 前端路由菜单树
     */
    List<RouteTreeVO> buildMenuTreeForRouter(String userId);

    /**
     * 分页查询菜单树
     *
     * @param query 查询条件
     * @return 分页菜单树
     */
    PageDTO<MenuTreeVO> getMenuTreePage(SysMenuTreeQuery query);
}
