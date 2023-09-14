package com.bilibili.mapper;

import com.bilibili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface UserMomentsMapper {

    void addUserMoments(UserMoment userMoment);
}
