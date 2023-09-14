package com.bilibili.service;

import com.bilibili.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface AuthRoleMenuService {
    List<AuthRoleMenu> getAuthRoleMenuByRoleIds(Set<Long> roleIdSet);
}
