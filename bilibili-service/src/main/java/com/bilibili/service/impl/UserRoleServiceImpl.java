package com.bilibili.service.impl;

import com.bilibili.domain.auth.UserRole;
import com.bilibili.mapper.UserRoleMapper;
import com.bilibili.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleMapper.getUserRoleByUserId(userId);
    }

    @Override
    public void addUserRole(UserRole userRole) {
       userRoleMapper.addUserRole(userRole);
    }
}
