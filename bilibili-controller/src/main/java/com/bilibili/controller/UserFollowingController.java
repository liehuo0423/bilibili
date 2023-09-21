package com.bilibili.controller;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.FollowingGroup;
import com.bilibili.domain.Response;
import com.bilibili.domain.UserFollowing;
import com.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
@RequestMapping("/users")
public class UserFollowingController {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserFollowingService userFollowingService;
    @PostMapping(value = "/followings",produces = {"application/json;charset=UTF-8"})
    public Response<String> addUserFollowing(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowing(userFollowing);
        return Response.success();
    }
    @GetMapping(value = "/fans",produces = {"application/json;charset=UTF-8"})
    public Response<List<UserFollowing>> getFansList(){
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> result = userFollowingService.getUserFans(userId);
        return new Response<>(result);
    }
    @GetMapping(value = "/followings",produces = {"application/json;charset=UTF-8"})
    public Response<List<FollowingGroup>> getFollowingGroup(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowing(userId);
        return new Response<>(result);
    }
    @PostMapping(value = "/following-groups",produces = {"application/json;charset=UTF-8"})
    public Response<Long> addFollowingGroup(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new Response<>(groupId);
    }
    @GetMapping(value = "/following-groups",produces = {"application/json;charset=UTF-8"})
    public Response<List<FollowingGroup>> getUserFollowingGroups(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> followingGroupList = userFollowingService.getUserFollowingGroups(userId);
        return new Response<>(followingGroupList);
    }
}
