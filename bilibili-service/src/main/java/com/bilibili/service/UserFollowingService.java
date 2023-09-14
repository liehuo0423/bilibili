package com.bilibili.service;

import com.bilibili.domain.FollowingGroup;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserInfo;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserFollowingService {
   void addUserFollowing(UserFollowing userFollowing);
   List<FollowingGroup> getUserFollowing(Long userId);
   List<UserFollowing> getUserFans(Long userId);

   Long addUserFollowingGroups(FollowingGroup followingGroup);

   List<FollowingGroup> getUserFollowingGroups(Long userId);

    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);
}
