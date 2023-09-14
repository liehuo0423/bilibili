package com.bilibili.mapper;

import com.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface FollowingGroupMapper {
    public FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup> getUserById(Long userId);

    Integer addFollowingGroup(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
