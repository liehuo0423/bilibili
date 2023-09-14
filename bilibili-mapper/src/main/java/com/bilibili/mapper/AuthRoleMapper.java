package com.bilibili.mapper;

import com.bilibili.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface AuthRoleMapper {
    AuthRole getAuthRoleByCode(String code);
}
