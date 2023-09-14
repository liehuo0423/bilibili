package com.bilibili.service;

import com.bilibili.domain.auth.AuthRole;
import com.bilibili.domain.auth.AuthRoleElementOperation;
import com.bilibili.domain.auth.AuthRoleMenu;
import com.bilibili.domain.auth.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface AuthRoleService {
    List<AuthRoleElementOperation> getAuthRoleElementOperationsByRoleIds(Set<Long> roleIdSet);

    List<AuthRoleMenu> getAuthRoleMenuByRoleIds(Set<Long> roleIdSet);

    AuthRole getAuthRoleByCode(String code);
}
