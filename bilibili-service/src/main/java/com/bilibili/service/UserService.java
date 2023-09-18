package com.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.domain.PageResult;
import com.bilibili.domain.User;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserService {
    void addUser(User user);

    String login(User user) throws Exception;

    User getUserInfo(Long userId);

    void updateUserInfos(UserInfo userInfo);
    User getUserById(Long id);

    List<UserInfo> getUserInfoByUserIds(Set<Long> followingIdSet);

    List<UserFollowing> getUserFollowings(Long userId);

    PageResult<UserInfo> pageListUserInfos(JSONObject params);

    Map<String, Object> loginForDts(User user) throws Exception;

    void logout(String refreshToken, Long userId);

    String refreshAccessToken(String refreshToken) throws Exception;

    List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList);
}
