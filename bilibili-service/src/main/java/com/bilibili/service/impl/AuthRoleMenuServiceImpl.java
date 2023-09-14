package com.bilibili.service.impl;

import com.bilibili.domain.auth.AuthRoleMenu;
import com.bilibili.mapper.AuthRoleMenuMapper;
import com.bilibili.service.AuthRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class AuthRoleMenuServiceImpl implements AuthRoleMenuService {
    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;

    @Override
    public List<AuthRoleMenu> getAuthRoleMenuByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuMapper.getAuthRoleMenuByRoleIds(roleIdSet);
    }
}
