package com.soybean.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.user.api.dto.SysUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.po.SysUser;
import com.soybean.user.api.query.SysUserQuery;
import com.soybean.user.api.vo.SysUserVO;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 新增系统用户
     *
     * @param sysUserDTO 用户信息
     * @return 是否成功
     */
    boolean addSysUser(SysUserDTO sysUserDTO);

    /**
     * 删除系统用户
     *
     * @param ids ID列表
     * @return 是否成功
     */
    boolean deleteSysUsers(List<String> ids);

    /**
     * 修改系统用户
     *
     * @param sysUserDTO 用户信息
     * @return 是否成功
     */
    boolean updateSysUser(SysUserDTO sysUserDTO);

    /**
     * 分页查询系统用户列表
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageDTO<SysUserVO> getSysUserPage(SysUserQuery query);

    /**
     * 获取所有系统用户（不包含密码）
     *
     * @return 用户列表
     */
    List<SysUserVO> getAllSysUsers();

    /**
     * 修改系统用户状态
     *
     * @param id     用户ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateSysUserStatus(String id, SysUserStatusEnum status);

    /**
     * 修改密码
     *
     * @param passwordUpdateDTO 密码更新信息
     * @return 是否成功
     */
    boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO);

    /**
     * 根据ID查询系统用户VO
     *
     * @param id 用户ID
     * @return 用户VO
     */
    SysUserVO getSysUserVOById(String id);
    
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getUserByUsername(String username);
}
