package com.bilibili.service;

import com.bilibili.domain.auth.UserRole;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserRoleService {
    List<UserRole> getUserRoleByUserId(Long userId);

    void addUserRole(UserRole userRole);
}
