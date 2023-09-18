package com.bilibili.service.impl;

import com.bilibili.mapper.UserCoinMapper;
import com.bilibili.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserCoinServiceImpl implements UserCoinService {
    @Autowired
    private UserCoinMapper userCoinMapper;
    @Override
    public Integer getUserCoinsAmount(Long userId) {
        return userCoinMapper.getUserCoinsAmount(userId);
    }

    @Override
    public void updateUserCoinsAmount(Long userId, Integer amount) {
        Date updateTime = new Date();
        userCoinMapper.updateUserCoinAmount(userId, amount, updateTime);
    }
}
