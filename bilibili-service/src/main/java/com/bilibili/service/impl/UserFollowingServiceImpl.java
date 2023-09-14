package com.bilibili.service.impl;

import com.bilibili.domain.FollowingGroup;
import com.bilibili.domain.User;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserInfo;
import com.bilibili.domain.constant.UserConstant;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.mapper.UserFollowingMapper;
import com.bilibili.service.FollowingGroupService;
import com.bilibili.service.UserFollowingService;
import com.bilibili.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bilibili.domain.constant.UserConstant.USER_FOLLOWING_GROUP_ALL_NAME;
import static com.bilibili.domain.constant.UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserFollowingServiceImpl implements UserFollowingService {
    @Autowired
    private UserFollowingMapper userFollowingMapper;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void addUserFollowing(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            FollowingGroup followingGroup = followingGroupService.getByType(USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            Long id = followingGroup.getId();
            userFollowing.setGroupId(id);
        } else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup == null) {
                throw new ConditionException("未知分组");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User dbFollowingUser = userService.getUserById(followingId);
        if (dbFollowingUser == null) {
            throw new ConditionException("用户不存在");
        }
        userFollowing.setCreateTime(new Date());
        userFollowingMapper.deleteUserFollowing(userFollowing.getUserId(), followingId);
        userFollowingMapper.addUserFollowing(userFollowing);
    }

    @Override
    public List<FollowingGroup> getUserFollowing(Long userId) {
        List<UserFollowing> list = userFollowingMapper.getUserFollowing(userId);
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet != null){
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }
        for (UserFollowing userFollowing : list) {
            for (UserInfo userInfo : userInfoList) {
                if (userInfo.getUserId().equals(userFollowing.getFollowingId())) {
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }

        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);

        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setUserInfoList(userInfoList);

        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);

        for (FollowingGroup group : groupList) {
            List<UserInfo> infoList = new ArrayList<>();
            for (UserFollowing userFollowing : list) {
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }

    @Override
    public List<UserFollowing> getUserFans(Long userId) {
        List<UserFollowing> fanList = userFollowingMapper.getUserFans(userId);
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> fanInfoList = new ArrayList<>();
        if(fanIdSet.size() > 0){
            fanInfoList = userService.getUserInfoByUserIds(fanIdSet);
        }
        List<UserFollowing> userFollowingList = userService.getUserFollowings(userId);

        for (UserFollowing userFollowing : fanList) {
            for (UserInfo userInfo : fanInfoList) {
                if(userFollowing.getUserId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(false);
                    userFollowing.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following : userFollowingList) {
                if(following.getFollowingId().equals(userFollowing.getUserId())){
                    userFollowing.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    @Override
    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

    @Override
    public List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId) {
        List<UserFollowing> userFollowing = userFollowingMapper.getUserFollowing(userId);
        for (UserInfo userInfo : list) {
            userInfo.setFollowed(false);
            for (UserFollowing following : userFollowing) {
                if(following.getFollowingId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        return list;
    }

}
