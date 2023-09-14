package com.bilibili.service.impl;

import com.bilibili.domain.FollowingGroup;
import com.bilibili.mapper.FollowingGroupMapper;
import com.bilibili.mapper.UserFollowingMapper;
import com.bilibili.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {
    @Autowired
    private FollowingGroupMapper followingGroupMapper;

    @Override
    public FollowingGroup getByType(String type) {
        FollowingGroup followingGroup = followingGroupMapper.getByType(type);
        return followingGroup;
    }
    @Override
    public FollowingGroup getById(Long id) {
        FollowingGroup followingGroup = followingGroupMapper.getById(id);
        return followingGroup;
    }

    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupMapper.getUserById(userId);
    }

    @Override
    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupMapper.addFollowingGroup(followingGroup);
    }

    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupMapper.getUserFollowingGroups(userId);
    }

}
