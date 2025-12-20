package com.soybean.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.common.core.utils.PasswordUtil;
import com.soybean.user.api.dto.AdminUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.AdminUserStatusEnum;
import com.soybean.user.api.po.AdminUser;
import com.soybean.user.api.query.AdminUserQuery;
import com.soybean.user.api.vo.AdminUserVO;
import com.soybean.user.mapper.AdminUserMapper;
import com.soybean.user.service.IAdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台管理员表 服务实现类
 * </p>
 *
 * @author dongdongjie
 * @since 2025-12-16
 */
@Slf4j
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    /**
     * 将AdminUser转换为AdminUserVO
     *
     * @param adminUser 管理员实体
     * @return 管理员VO
     */
    private AdminUserVO convertToVO(AdminUser adminUser) {
        if (adminUser == null) {
            return null;
        }
        
        AdminUserVO adminUserVO = new AdminUserVO();
        adminUserVO.setUid(adminUser.getUid());
        adminUserVO.setUsername(adminUser.getUsername());
        adminUserVO.setNickname(adminUser.getNickname());
        adminUserVO.setPhone(adminUser.getPhone());
        adminUserVO.setEmail(adminUser.getEmail());
        // 设置状态枚举
        AdminUserStatusEnum statusEnum = adminUser.getStatus();
        adminUserVO.setStatus(statusEnum);
        
        // 设置状态名称
        adminUserVO.setStatusName(statusEnum != null ? statusEnum.getDesc() : "");

        // 设置创建时间和更新时间
        adminUserVO.setCreateTime(adminUser.getCreateTime());
        adminUserVO.setUpdateTime(adminUser.getUpdateTime());

        return adminUserVO;
    }

    @Override
    public boolean addAdminUser(AdminUserDTO adminUserDTO) {
        // 检查用户名是否已存在
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminUserDTO.getUsername());
        AdminUser existUser = this.getOne(queryWrapper);
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 转换DTO为实体
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(adminUserDTO.getUsername());
        
        // 加密密码
        String encryptedPassword = PasswordUtil.encrypt(adminUserDTO.getPassword());
        adminUser.setPassword(encryptedPassword);
        
        adminUser.setNickname(adminUserDTO.getNickname());
        adminUser.setPhone(adminUserDTO.getPhone());
        adminUser.setEmail(adminUserDTO.getEmail());
        
        // 设置默认状态为启用
        adminUser.setStatus(adminUserDTO.getStatus() != null ? adminUserDTO.getStatus() : AdminUserStatusEnum.ENABLED);

        // 生成用户ID
        adminUser.setUid(String.valueOf(System.currentTimeMillis()));

        return this.save(adminUser);
    }

    @Override
    public boolean deleteAdminUsers(List<String> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public boolean updateAdminUser(AdminUserDTO adminUserDTO) {
        // 检查用户是否存在
        AdminUser existUser = this.getById(adminUserDTO.getUid());
        if (existUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 如果修改了用户名，检查新用户名是否已存在
        if (!existUser.getUsername().equals(adminUserDTO.getUsername())) {
            QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", adminUserDTO.getUsername());
            AdminUser userWithSameName = this.getOne(queryWrapper);
            if (userWithSameName != null) {
                throw new RuntimeException("用户名已存在");
            }
        }

        // 转换DTO为实体
        AdminUser adminUser = new AdminUser();
        adminUser.setUid(adminUserDTO.getUid());
        adminUser.setUsername(adminUserDTO.getUsername());
        // 如果DTO中提供了密码，则更新密码，否则保留原密码
        if (adminUserDTO.getPassword() != null && !adminUserDTO.getPassword().isEmpty()) {
            // 加密密码
            String encryptedPassword = PasswordUtil.encrypt(adminUserDTO.getPassword());
            adminUser.setPassword(encryptedPassword);
        } else {
            adminUser.setPassword(existUser.getPassword());
        }
        adminUser.setNickname(adminUserDTO.getNickname());
        adminUser.setPhone(adminUserDTO.getPhone());
        adminUser.setEmail(adminUserDTO.getEmail());
        adminUser.setStatus(adminUserDTO.getStatus());

        return this.updateById(adminUser);
    }

    @Override
    public PageDTO<AdminUserVO> getAdminUserPage(AdminUserQuery query) {

        Page<AdminUser> page = query.toMpPageDefaultSortByCreateTimeDesc(); // 降序排序
        LambdaQueryWrapper<AdminUser> queryWrapper = Wrappers.<AdminUser>lambdaQuery();

        if (query.getUsername() != null && !query.getUsername().trim().isEmpty()) {
            queryWrapper.like(AdminUser::getUsername, query.getUsername());
        }
        if (query.getNickname() != null && !query.getNickname().trim().isEmpty()) {
            queryWrapper.like(AdminUser::getNickname, query.getNickname());
        }
        if (query.getPhone() != null && !query.getPhone().trim().isEmpty()) {
            queryWrapper.like(AdminUser::getPhone, query.getPhone());
        }
        if (query.getEmail() != null && !query.getEmail().trim().isEmpty()) {
            queryWrapper.like(AdminUser::getEmail, query.getEmail());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(AdminUser::getStatus, query.getStatus());
        }

        this.page(page, queryWrapper);
        
        // 使用PageDTO.of方法，直接转换
        return PageDTO.of(page, AdminUserVO.class);

    }

    @Override
    public List<AdminUserVO> getAllAdminUsers() {
        List<AdminUser> list = this.list();
        // 转换为VO对象
        return list.stream().map(this::convertToVO).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean updateAdminUserStatus(String id, AdminUserStatusEnum status) {
        AdminUser adminUser = new AdminUser();
        adminUser.setUid(id);
        adminUser.setStatus(status);
        return this.updateById(adminUser);
    }

    @Override
    public AdminUserVO login(AdminUserDTO adminUserDTO) {
        // 查询用户
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", adminUserDTO.getUsername());
        AdminUser adminUser = this.getOne(queryWrapper);
        
        if (adminUser == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (adminUser.getStatus() == AdminUserStatusEnum.DISABLED) {
            throw new RuntimeException("账户已被禁用");
        }
        
        // 验证密码
        if (!PasswordUtil.verify(adminUserDTO.getPassword(), adminUser.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        // 转换为VO对象
        return convertToVO(adminUser);
    }

    @Override
    public boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        // 查询用户
        String uid = passwordUpdateDTO.getUid();
        AdminUser adminUser = this.getById(uid);
        if (adminUser == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证原密码
        if (!PasswordUtil.verify(passwordUpdateDTO.getOldPassword(), adminUser.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        // 加密新密码
        String encryptedPassword = PasswordUtil.encrypt(passwordUpdateDTO.getNewPassword());
        
        // 更新密码
        adminUser.setPassword(encryptedPassword);
        
        return this.updateById(adminUser);
    }
    
    @Override
    public AdminUserVO getAdminUserVOById(String id) {
        AdminUser adminUser = this.getById(id);
        if (adminUser != null) {
            return convertToVO(adminUser);
        }
        return null;
    }
}
