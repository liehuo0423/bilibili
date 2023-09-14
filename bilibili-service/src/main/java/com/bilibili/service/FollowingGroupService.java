package com.bilibili.service;

import com.bilibili.domain.FollowingGroup;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface FollowingGroupService {
    FollowingGroup getByType(String type);
    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(Long userId);

    void addFollowingGroup(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
