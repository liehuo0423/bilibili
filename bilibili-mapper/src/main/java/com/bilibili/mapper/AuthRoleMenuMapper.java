package com.bilibili.mapper;

import com.bilibili.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface AuthRoleMenuMapper {
    List<AuthRoleMenu> getAuthRoleMenuByRoleIds(@Param("roleIds")Set<Long> roleIdSet);
}
