package com.bilibili.controller;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.Response;
import com.bilibili.domain.auth.UserAuthorities;
import com.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
public class UserAuthController {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserAuthService userAuthService;
    @GetMapping("user-authorities")
    public Response<UserAuthorities> getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new Response<>(userAuthorities);
    }

}
