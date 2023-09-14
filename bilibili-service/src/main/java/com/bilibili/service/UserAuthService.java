package com.bilibili.service;

import com.bilibili.domain.auth.UserAuthorities;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserAuthService {
    UserAuthorities getUserAuthorities(Long userId);

    void addUserDefaultRole(Long id);
}
