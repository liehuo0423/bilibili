package com.bilibili.mapper;

import com.bilibili.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface UserRoleMapper {
    List<UserRole> getUserRoleByUserId(Long userId);

    void addUserRole(UserRole userRole);
}
