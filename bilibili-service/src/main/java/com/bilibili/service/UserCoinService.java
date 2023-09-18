package com.bilibili.service;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserCoinService {
    Integer getUserCoinsAmount(Long userId);
    void updateUserCoinsAmount(Long userId, Integer amount);
}
