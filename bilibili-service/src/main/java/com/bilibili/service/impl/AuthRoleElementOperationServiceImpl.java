package com.bilibili.service.impl;

import com.bilibili.domain.PageResult;
import com.bilibili.domain.auth.AuthElementOperation;
import com.bilibili.domain.auth.AuthRoleElementOperation;
import com.bilibili.domain.auth.AuthRoleMenu;
import com.bilibili.mapper.AuthRoleElementOperationMapper;
import com.bilibili.service.AuthRoleElementOperationService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class AuthRoleElementOperationServiceImpl implements AuthRoleElementOperationService {
    @Autowired
    private AuthRoleElementOperationMapper authRoleElementOperationMapper;
    @Override
    public List<AuthRoleElementOperation> getAuthRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationMapper.getAuthRoleElementOperationsByRoleIds(roleIdSet);
    }
}
