package com.soybean.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.user.api.dto.AdminUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import com.soybean.user.api.po.AdminUser;
import com.soybean.user.api.query.AdminUserQuery;
import com.soybean.user.api.vo.AdminUserVO;

import java.util.List;

/**
 * <p>
 * 后台管理员表 服务类
 * </p>
 *
 * @author dongdongjie
 * @since 2025-12-16
 */
public interface IAdminUserService extends IService<AdminUser> {

    /**
     * 新增管理员
     *
     * @param adminUserDTO 管理员信息
     * @return 是否成功
     */
    boolean addAdminUser(AdminUserDTO adminUserDTO);

    /**
     * 删除管理员
     *
     * @param ids ID列表
     * @return 是否成功
     */
    boolean deleteAdminUsers(List<String> ids);

    /**
     * 修改管理员
     *
     * @param adminUserDTO 管理员信息
     * @return 是否成功
     */
    boolean updateAdminUser(AdminUserDTO adminUserDTO);

    /**
     * 分页查询管理员列表
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageDTO<AdminUserVO> getAdminUserPage(AdminUserQuery query);

    /**
     * 获取所有管理员（不包含密码）
     *
     * @return 管理员列表
     */
    List<AdminUserVO> getAllAdminUsers();

    /**
     * 修改管理员状态
     *
     * @param id     管理员ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateAdminUserStatus(String id, AdminUserStatusEnum status);

    /**
     * 管理员登录
     *
     * @param adminUserDTO 登录信息
     * @return 管理员信息（不含密码）
     */
    AdminUserVO login(AdminUserDTO adminUserDTO);

    /**
     * 修改密码
     *
     * @param passwordUpdateDTO 密码更新信息
     * @return 是否成功
     */
    boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO);
    
    /**
     * 根据ID查询管理员VO
     *
     * @param id 管理员ID
     * @return 管理员VO
     */
    AdminUserVO getAdminUserVOById(String id);
}
