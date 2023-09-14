package com.bilibili.service.impl;

import com.bilibili.domain.auth.*;
import com.bilibili.mapper.AuthRoleMapper;
import com.bilibili.service.AuthRoleElementOperationService;
import com.bilibili.service.AuthRoleMenuService;
import com.bilibili.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {
    @Autowired
    private AuthRoleMapper authRoleMapper;
    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;
    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    @Override
    public List<AuthRoleElementOperation> getAuthRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationService.getAuthRoleElementOperationsByRoleIds(roleIdSet);
    }

    @Override
    public List<AuthRoleMenu> getAuthRoleMenuByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuService.getAuthRoleMenuByRoleIds(roleIdSet);
    }

    @Override
    public AuthRole getAuthRoleByCode(String code) {
        return authRoleMapper.getAuthRoleByCode(code);
    }

}
