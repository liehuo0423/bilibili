package com.bilibili.service;

import com.bilibili.domain.UserMoment;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserMomentsService {
    void addUserMoments(UserMoment userMoment) throws Exception;

    List<UserMoment> getSubscribedUserMoments(Long userId);
}
