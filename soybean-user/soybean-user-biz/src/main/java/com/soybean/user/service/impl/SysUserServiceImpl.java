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
import com.soybean.user.api.vo.SysUserCreateResultVO;
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
    private SysUserVO convertToSysUserVO(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }

        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setId(sysUser.getUserId());
        sysUserVO.setUserName(sysUser.getUsername());
        sysUserVO.setNickName(sysUser.getNickname());
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
    public SysUserCreateResultVO addSysUser(SysUserDTO sysUserDTO) {
        // 验证用户名唯一性
        validateUsernameUnique(sysUserDTO.getUserName(), null);

        // 转换DTO为实体
        SysUser sysUser = new SysUser();
        sysUser.setUsername(sysUserDTO.getUserName());

        // 生成随机密码（如果未提供）
        String originalPassword = generateRandomPassword(8);
        
        // 加密密码
        String encryptedPassword = PasswordUtil.encrypt(originalPassword);
        sysUser.setPassword(encryptedPassword);

        sysUser.setNickname(sysUserDTO.getNickName());
        sysUser.setPhone(sysUserDTO.getPhone());
        sysUser.setEmail(sysUserDTO.getEmail());
        sysUser.setAvatar(sysUserDTO.getAvatar());
        sysUser.setGender(sysUserDTO.getGender());

        // 设置默认状态为启用
        sysUser.setStatus(sysUserDTO.getStatus() != null ? sysUserDTO.getStatus() : SysUserStatusEnum.NORMAL);

        // 设置删除标志为未删除
        sysUser.setDelFlag(SysUserDelFlagEnum.NORMAL);

        // 保存用户
        boolean success = this.save(sysUser);
        if (!success) {
            return null;
        }
        
        // 构建返回结果
        SysUserCreateResultVO result = new SysUserCreateResultVO();
        result.setId(sysUser.getUserId());
        result.setUserName(sysUser.getUsername());
        result.setGeneratedPassword(originalPassword);
        
        return result;
    }
    
    /**
     * 验证用户名唯一性，如果用户名已存在则抛出异常
     * @param username 用户名
     * @param excludeUserId 排除的用户ID（可为null）
     * @throws BusinessException 如果用户名已存在
     */
    private void validateUsernameUnique(String username, String excludeUserId) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username);
                
        // 如果需要排除特定用户
        if (excludeUserId != null) {
            queryWrapper.ne(SysUser::getUserId, Long.valueOf(excludeUserId));
        }
        
        if (this.getOne(queryWrapper) != null) {
            throw new BusinessException("用户名已存在");
        }
    }

    private void validateUserExists(SysUser sysUser) {
        if (sysUser == null || SysUserDelFlagEnum.DELETED.equals(sysUser.getDelFlag())) {
            throw new BusinessException("用户不存在");
        }
    }

    /**
     * 生成随机密码
     * @param length 密码长度
     * @return 随机密码
     */
    private String generateRandomPassword(int length) {
        // 定义字符集
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        // 生成随机密码
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * charSet.length());
            password.append(charSet.charAt(index));
        }
        
        return password.toString();
    }

    @Override
    public boolean deleteSysUsers(List<String> ids) {
        // 检查是否包含admin账号(ID为1)，不允许删除admin账号
        if (ids.contains("1")) {
            throw new BusinessException("不允许删除admin账号");
        }
        
        // 使用 MyBatis-Plus 的物理删除方法
        return removeByIds(ids);
    }

    @Override
    public boolean updateSysUser(SysUserDTO sysUserDTO) {
        // 检查用户是否存在
        SysUser existUser = getById(sysUserDTO.getId());
        validateUserExists(existUser);

        // 如果修改了用户名，验证新用户名唯一性
        if (!existUser.getUsername().equals(sysUserDTO.getUserName())) {
            validateUsernameUnique(sysUserDTO.getUserName(), sysUserDTO.getId());
        }

        // 转换DTO为实体
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysUserDTO.getId());
        sysUser.setUsername(sysUserDTO.getUserName());

        sysUser.setNickname(sysUserDTO.getNickName());
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
                .eq(query.getStatus() != null, SysUser::getStatus, query.getStatus())
                .like(query.getUserName() != null && !query.getUserName().trim().isEmpty(), SysUser::getUsername, query.getUserName())
                .like(query.getNickName() != null && !query.getNickName().trim().isEmpty(), SysUser::getNickname, query.getNickName())
                .like(query.getPhone() != null && !query.getPhone().trim().isEmpty(), SysUser::getPhone, query.getPhone())
                .like(query.getEmail() != null && !query.getEmail().trim().isEmpty(), SysUser::getEmail, query.getEmail());

        this.page(page, queryWrapper);

        // 使用PageDTO.of方法，传入转换函数
        return PageDTO.of(page, this::convertToSysUserVO);
    }

    @Override
    public List<SysUserVO> getAllSysUsers() {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDelFlag, SysUserDelFlagEnum.NORMAL); // 只查询未删除的用户
        List<SysUser> list = list(queryWrapper);
        // 转换为VO对象
        return list.stream().map(this::convertToSysUserVO).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean updateSysUserStatus(String id, SysUserStatusEnum status) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser.setStatus(status);
        return updateById(sysUser);
    }

    @Override
    public boolean updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        // 查询用户
        String userId = passwordUpdateDTO.getId();
        SysUser sysUser = getById(userId);
        validateUserExists(sysUser);

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
        SysUser sysUser = getById(id);
        if (sysUser != null && !SysUserDelFlagEnum.DELETED.equals(sysUser.getDelFlag())) {
            return convertToSysUserVO(sysUser);
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
    public SysUserVO getCurrentUserInfo(String userId) {
        // 获取用户基本信息
        SysUser sysUser = getById(userId);
        validateUserExists(sysUser);

        // 转换为UserInfoVO
        SysUserVO sysUserVO = convertToSysUserVO(sysUser);

        // 从Redis中获取用户角色列表
        List<String> roles = SecurityUtil.getRoles();
        sysUserVO.setRoles(roles);

        // 从Redis中获取用户权限列表
        List<String> permissions = SecurityUtil.getPermissions();
        sysUserVO.setPermissions(permissions);

        return sysUserVO;
    }
}
