package com.bilibili.service.impl;

import com.bilibili.domain.auth.*;
import com.bilibili.domain.constant.AuthRoleConstant;
import com.bilibili.mapper.UserAuthMapper;
import com.bilibili.service.AuthRoleService;
import com.bilibili.service.UserAuthService;
import com.bilibili.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthRoleService authRoleService;

    @Override
    public UserAuthorities getUserAuthorities(Long userId) {
        List<UserRole> userRoles = userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdSet = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<AuthRoleElementOperation> authRoleElementOperationList = authRoleService.getAuthRoleElementOperationsByRoleIds(roleIdSet);
        List<AuthRoleMenu> authRoleMenus = authRoleService.getAuthRoleMenuByRoleIds(roleIdSet);
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setRoleElementOperationList(authRoleElementOperationList);
        userAuthorities.setRoleMenuList(authRoleMenus);
        return userAuthorities;
    }

    @Override
    public void addUserDefaultRole(Long id) {
        AuthRole authRole = authRoleService.getAuthRoleByCode(AuthRoleConstant.ROLE_LV0);
        Long roleId = authRole.getId();
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(new Date());
        userRoleService.addUserRole(userRole);
    }
}
