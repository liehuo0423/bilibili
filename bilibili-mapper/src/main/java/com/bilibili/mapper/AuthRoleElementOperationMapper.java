package com.bilibili.mapper;

import com.bilibili.domain.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface AuthRoleElementOperationMapper {
    List<AuthRoleElementOperation> getAuthRoleElementOperationsByRoleIds(@Param("roleIds") Set<Long> roleIdSet);
}
