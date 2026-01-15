package com.soybean.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.upms.api.dto.PasswordUpdateDTO;
import com.soybean.upms.api.dto.SysUserDTO;
import com.soybean.common.core.enums.StatusEnum;
import com.soybean.upms.api.po.SysUser;
import com.soybean.upms.api.query.SysUserQuery;
import com.soybean.upms.api.vo.SysUserCreateResultVO;
import com.soybean.upms.api.vo.SysUserVO;
import com.soybean.upms.api.vo.UserInfoVO;

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
     * @return 新增结果，包含用户ID和生成的密码
     */
    SysUserCreateResultVO addSysUser(SysUserDTO sysUserDTO);

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
    boolean updateSysUserStatus(String id, StatusEnum status);

    /**
     * 修改密码
     *
     * @param passwordUpdateDTO 密码更新信息
     * @return 是否成功
     */
    boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO);
    
    /**
     * 管理员修改用户密码
     *
     * @param passwordUpdateDTO 管理员密码更新信息
     * @return 是否成功
     */
    boolean adminUpdatePassword(PasswordUpdateDTO passwordUpdateDTO);

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

    /**
     * 获取当前登录用户信息（包括基本信息、角色和权限）
     *
     * @param userId 用户ID
     * @return 用户信息（包含角色和权限）
     */
    UserInfoVO getCurrentUserInfo(String userId);
}
