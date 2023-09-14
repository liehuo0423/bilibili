package com.bilibili.mapper;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.domain.RefreshToken;
import com.bilibili.domain.User;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface UserMapper {

    void addUser(User user);

    User getUserByPhone(String phone);

    void addUserInfo(UserInfo userInfo);

    User getUserById(Long id);

    UserInfo getUserInfoByUserId(Long userId);

    void updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    List<UserFollowing> getUserFollowings(Long userId);

    Integer pageCountUserInfos(Map<String, Object> params);

    List<UserInfo> pageListUserInfos(JSONObject params);

    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken,
                               @Param("userId") Long userId);

    Integer addRefreshToken(@Param("refreshToken") String refreshToken,
                            @Param("userId") Long userId,
                            @Param("createTime") Date createTime);

    RefreshToken getRefreshTokenDetail(String refreshToken);
}
