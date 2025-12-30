package com.soybean.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soybean.common.mybatis.dto.PageDTO;
import com.soybean.common.core.utils.PasswordUtil;
import com.soybean.user.api.dto.SysUserDTO;
import com.soybean.user.api.dto.PasswordUpdateDTO;
import com.soybean.user.api.enums.SysUserDelFlagEnum;
import com.soybean.user.api.enums.SysUserStatusEnum;
import com.soybean.user.api.po.SysUser;
import com.soybean.user.api.query.SysUserQuery;
import com.soybean.user.api.vo.SysUserVO;
import com.soybean.user.api.vo.UserInfoVO;
import com.soybean.user.mapper.SysUserMapper;
import com.soybean.user.service.ISysUserService;
import com.soybean.common.core.exception.BusinessException;
import com.soybean.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author soybean
 * @since 2024-07-07
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * 将SysUser转换为AdminUserVO
     *
     * @param sysUser 系统用户实体
     * @return 用户VO
     */
    private SysUserVO convertToVO(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }

        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setUserId(sysUser.getUserId());
        sysUserVO.setUsername(sysUser.getUsername());
        sysUserVO.setNickname(sysUser.getNickname());
        sysUserVO.setPhone(sysUser.getPhone());
        sysUserVO.setEmail(sysUser.getEmail());
        sysUserVO.setAvatar(sysUser.getAvatar());
        sysUserVO.setGender(sysUser.getGender());

        // 设置状态枚举
        sysUserVO.setStatus(sysUser.getStatus());

        // 设置状态名称
        sysUserVO.setStatusName(sysUser.getStatus() != null ? sysUser.getStatus().getDesc() : "");

        // 设置创建时间和更新时间
        sysUserVO.setCreateTime(sysUser.getCreateTime());
        sysUserVO.setUpdateTime(sysUser.getUpdateTime());

        return sysUserVO;
    }

    @Override
    public boolean addSysUser(SysUserDTO sysUserDTO) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, sysUserDTO.getUsername())
                .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL.getValue());
        SysUser existUser = this.getOne(queryWrapper);
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 转换DTO为实体
        SysUser sysUser = new SysUser();
        sysUser.setUsername(sysUserDTO.getUsername());

        // 加密密码
        String encryptedPassword = PasswordUtil.encrypt(sysUserDTO.getPassword());
        sysUser.setPassword(encryptedPassword);

        sysUser.setNickname(sysUserDTO.getNickname());
        sysUser.setPhone(sysUserDTO.getPhone());
        sysUser.setEmail(sysUserDTO.getEmail());
        sysUser.setAvatar(sysUserDTO.getAvatar());
        sysUser.setGender(sysUserDTO.getGender());

        // 设置默认状态为启用
        sysUser.setStatus(sysUserDTO.getStatus() != null ? sysUserDTO.getStatus() : SysUserStatusEnum.NORMAL);

        // 设置删除标志为未删除
        sysUser.setDelFlag(SysUserDelFlagEnum.NORMAL);

        return this.save(sysUser);
    }

    @Override
    public boolean deleteSysUsers(List<String> ids) {
        // 逻辑删除，将del_flag设置为2
        return ids.stream().allMatch(id -> {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(id);
            sysUser.setDelFlag(SysUserDelFlagEnum.DELETED);
            return this.updateById(sysUser);
        });
    }

    @Override
    public boolean updateSysUser(SysUserDTO sysUserDTO) {
        // 检查用户是否存在
        SysUser existUser = this.getById(sysUserDTO.getUserId());
        if (existUser == null || SysUserDelFlagEnum.DELETED.getValue().equals(existUser.getDelFlag())) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改了用户名，检查新用户名是否已存在
        if (!existUser.getUsername().equals(sysUserDTO.getUsername())) {
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                    .eq(SysUser::getUsername, sysUserDTO.getUsername())
                    .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL.getValue())
                    .ne(SysUser::getUserId, Long.valueOf(sysUserDTO.getUserId()));
            SysUser userWithSameName = this.getOne(queryWrapper);
            if (userWithSameName != null) {
                throw new BusinessException("用户名已存在");
            }
        }

        // 转换DTO为实体
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysUserDTO.getUserId());
        sysUser.setUsername(sysUserDTO.getUsername());

        // 如果DTO中提供了密码，则更新密码，否则保留原密码
        if (sysUserDTO.getPassword() != null && !sysUserDTO.getPassword().isEmpty()) {
            // 加密密码
            String encryptedPassword = PasswordUtil.encrypt(sysUserDTO.getPassword());
            sysUser.setPassword(encryptedPassword);
        } else {
            sysUser.setPassword(existUser.getPassword());
        }

        sysUser.setNickname(sysUserDTO.getNickname());
        sysUser.setPhone(sysUserDTO.getPhone());
        sysUser.setEmail(sysUserDTO.getEmail());
        sysUser.setAvatar(sysUserDTO.getAvatar());
        sysUser.setGender(sysUserDTO.getGender());

        // 设置状态
        sysUser.setStatus(sysUserDTO.getStatus());

        return this.updateById(sysUser);
    }

    @Override
    public PageDTO<SysUserVO> getSysUserPage(SysUserQuery query) {
        Page<SysUser> page = query.toMpPageDefaultSortByCreateTimeDesc(); // 降序排序
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL); // 只查询未删除的用户

        if (query.getUsername() != null && !query.getUsername().trim().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, query.getUsername());
        }
        if (query.getNickname() != null && !query.getNickname().trim().isEmpty()) {
            queryWrapper.like(SysUser::getNickname, query.getNickname());
        }
        if (query.getPhone() != null && !query.getPhone().trim().isEmpty()) {
            queryWrapper.like(SysUser::getPhone, query.getPhone());
        }
        if (query.getEmail() != null && !query.getEmail().trim().isEmpty()) {
            queryWrapper.like(SysUser::getEmail, query.getEmail());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(SysUser::getStatus, query.getStatus());
        }

        this.page(page, queryWrapper);

        // 使用PageDTO.of方法，直接转换
        return PageDTO.of(page, SysUserVO.class);
    }

    @Override
    public List<SysUserVO> getAllSysUsers() {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL); // 只查询未删除的用户
        List<SysUser> list = this.list(queryWrapper);
        // 转换为VO对象
        return list.stream().map(this::convertToVO).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean updateSysUserStatus(String id, SysUserStatusEnum status) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser.setStatus(status);
        return this.updateById(sysUser);
    }

    @Override
    public boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        // 查询用户
        String userId = passwordUpdateDTO.getUserId();
        SysUser sysUser = this.getById(userId);
        if (sysUser == null || SysUserDelFlagEnum.DELETED.getValue().equals(sysUser.getDelFlag())) {
            throw new BusinessException("用户不存在");
        }

        // 验证原密码
        if (!PasswordUtil.verify(passwordUpdateDTO.getOldPassword(), sysUser.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        // 加密新密码
        String encryptedPassword = PasswordUtil.encrypt(passwordUpdateDTO.getNewPassword());

        // 更新密码
        sysUser.setPassword(encryptedPassword);

        return this.updateById(sysUser);
    }

    @Override
    public SysUserVO getSysUserVOById(String id) {
        SysUser sysUser = this.getById(id);
        if (sysUser != null && !SysUserDelFlagEnum.DELETED.getValue().equals(sysUser.getDelFlag())) {
            return convertToVO(sysUser);
        }
        return null;
    }
    
    @Override
    public SysUser getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL);
        
        return this.getOne(queryWrapper);
    }

    @Override
    public UserInfoVO getCurrentUserInfo(String userId) {
        // 获取用户基本信息
        SysUser sysUser = this.getById(userId);
        if (sysUser == null || SysUserDelFlagEnum.DELETED.getValue().equals(sysUser.getDelFlag())) {
            return null;
        }

        // 转换为UserInfoVO
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(sysUser.getUserId());
        userInfoVO.setUsername(sysUser.getUsername());
        userInfoVO.setNickname(sysUser.getNickname());
        userInfoVO.setPhone(sysUser.getPhone());
        userInfoVO.setEmail(sysUser.getEmail());
        userInfoVO.setAvatar(sysUser.getAvatar());
        userInfoVO.setGender(sysUser.getGender());
        userInfoVO.setStatus(sysUser.getStatus());
        userInfoVO.setCreateTime(sysUser.getCreateTime());
        userInfoVO.setUpdateTime(sysUser.getUpdateTime());

        // 从Redis中获取用户角色列表
        List<String> roles = SecurityUtil.getRoles();
        userInfoVO.setRoles(roles);

        // 从Redis中获取用户权限列表
        List<String> permissions = SecurityUtil.getPermissions();
        userInfoVO.setPermissions(permissions);

        return userInfoVO;
    }
}
