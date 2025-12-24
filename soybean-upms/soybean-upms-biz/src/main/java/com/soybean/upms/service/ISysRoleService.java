
package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.SysRoleDTO;
import com.soybean.upms.api.enums.SysRoleStatusEnum;
import com.soybean.upms.api.po.SysRole;
import com.soybean.upms.api.query.SysRoleQuery;
import com.soybean.upms.api.vo.SysRoleVO;

import java.util.List;
import java.util.Set;

/**
 * 角色表 服务类
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据条件分页查询角色数据
     *
     * @param query 查询条件
     * @return 分页角色信息
     */
    PageDTO<SysRoleVO> getRolePage(SysRoleQuery query);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleVO> selectRolesByUserId(Long userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<SysRoleVO> selectRoleAll();

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    List<Long> selectRoleListByUserId(Long userId);

    /**
     * 新增保存角色信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    boolean insertRole(SysRoleDTO roleDTO);

    /**
     * 修改保存角色信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    boolean updateRole(SysRoleDTO roleDTO);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态
     * @return 结果
     */
    boolean updateRoleStatus(Long roleId, SysRoleStatusEnum status);

    /**
     * 修改数据权限信息
     *
     * @param roleDTO 角色信息
     * @return 结果
     */
    boolean authDataScope(SysRoleDTO roleDTO);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    boolean deleteRoleByIds(List<Long> roleIds);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleNameUnique(SysRoleDTO role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleKeyUnique(SysRoleDTO role);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    void checkRoleAllowed(SysRoleDTO role);

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    void checkRoleDataScope(Long roleId);

    /**
     * 校验角色是否存在
     *
     * @param roleId 角色ID
     * @return 是否存在
     */
    boolean checkRoleExists(Long roleId);

    /**
     * 校验多个角色是否都存在
     *
     * @param roleIds 角色ID列表
     * @return 是否全部存在
     */
    boolean checkRolesExists(List<Long> roleIds);


    /**
     * 根据用户ID获取角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Long userId);
}
