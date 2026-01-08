
package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.upms.api.dto.SysMenuDTO;
import com.soybean.upms.api.po.SysMenu;
import com.soybean.upms.api.query.SysMenuQuery;
import com.soybean.upms.api.query.SysMenuTreeQuery;
import com.soybean.upms.api.vo.MenuTreeVO;
import com.soybean.upms.api.vo.RouteTreeVO;
import com.soybean.upms.api.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单权限Service接口
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID获取菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> getMenusByUserId(String userId);

    /**
     * 根据用户ID获取权限标识
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> getPermissionsByUserId(String userId);

    /**
     * 新增菜单及按钮
     *
     * @param menuDTO 菜单DTO
     * @return 结果
     */
    boolean saveMenuWithButtons(SysMenuDTO menuDTO);

    /**
     * 更新菜单及按钮
     *
     * @param menuDTO 菜单DTO
     * @return 结果
     */
    boolean updateMenuWithButtons(SysMenuDTO menuDTO);

    /**
     * 批量删除菜单
     *
     * @param ids 菜单ID数组
     * @return 结果
     */
    boolean deleteMenuWithButtons(List<Long> ids);

    /**
     * 查询所有菜单
     *
     * @param query 查询条件
     * @return 菜单VO列表
     */
    List<SysMenuVO> listAllMenus(SysMenuQuery query);

    /**
     * 查询所有菜单树
     *
     * @return 菜单树列表
     */
    List<MenuTreeVO> listAllMenusTree();

    /**
     * 根据菜单ID查询菜单详情
     *
     * @param id 菜单ID
     * @return 菜单树详情
     */
    MenuTreeVO getMenuById(Long id);

    /**
     * 分页查询菜单树
     *
     * @param query 查询条件
     * @return 分页菜单树
     */
    Page<MenuTreeVO> pageMenuTree(SysMenuTreeQuery query);

    /**
     * 获取当前用户的路由树
     *
     * @return 路由树列表
     */
    List<RouteTreeVO> getCurrentUserRouteTree();


    /**
     * 构建路由树
     *
     * @param menuList 菜单列表
     * @param parentId 父菜单ID
     * @return 路由树列表
     */
    List<RouteTreeVO> buildRouteTree(List<SysMenu> menuList, Long parentId);

    /**
     * 获取所有静态菜单路由树
     *
     * @return 静态菜单路由树列表
     */
    List<RouteTreeVO> getStaticMenuRouteTree();
}
