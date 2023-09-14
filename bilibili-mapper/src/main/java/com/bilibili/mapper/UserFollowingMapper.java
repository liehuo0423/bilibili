package com.bilibili.mapper;

import com.bilibili.domain.FollowingGroup;
import com.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface UserFollowingMapper {
    Integer deleteUserFollowing(@Param("userID") Long userID, @Param("followingId") Long followingId);

    Integer addUserFollowing(UserFollowing userFollowing);

    List<UserFollowing> getUserFollowing(Long userId);

    List<UserFollowing> getUserFans(Long userId);
}
