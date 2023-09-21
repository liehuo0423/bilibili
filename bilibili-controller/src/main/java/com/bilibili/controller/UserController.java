package com.bilibili.controller;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.*;
import com.bilibili.service.UserFollowingService;
import com.bilibili.service.UserService;
import com.bilibili.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;

    @GetMapping(value = "/users",produces = {"application/json;charset=UTF-8"})
    public Response<User> getCurrentId(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return new Response<>(user);
    }
    @GetMapping(value = "/rsa-pds",produces = {"application/json;charset=UTF-8"})
    public Response<String> getPublicKey(){
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return Response.success(publicKeyStr);
    }

    @PostMapping(value = "/users",produces = {"application/json;charset=UTF-8"})
    public Response<String> saveUser(@RequestBody User user){
        userService.addUser(user);
        return Response.success();
    }

    @PostMapping(value = "/user-tokens",produces = {"application/json;charset=UTF-8"})
    public Response<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return Response.success(token);
    }

    @PostMapping(value = "/user-dts",produces = {"application/json;charset=UTF-8"})
    public Response<Map<String,Object>> loginForDts(@RequestBody User user) throws Exception {
       Map<String ,Object> map = userService.loginForDts(user);
       return new Response<>(map);
    }
    @PostMapping(value = "/access-tokens",produces = {"application/json;charset=UTF-8"})
    public Response<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(refreshToken);
        return new Response<>(accessToken);
    }
    @DeleteMapping(value = "refresh-tokens",produces = {"application/json;charset=UTF-8"})
    public Response<String> logout(HttpServletRequest request){
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.logout(refreshToken,userId);
        return Response.success();
    }

    @PutMapping("/user-infos")
    public Response<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return Response.success();
    }

    @GetMapping("user-infos")
    public Response<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no",no);
        params.put("size",size);
        params.put("nick",nick);
        params.put("userId",userId);
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        if (result.getTotal()>0){
            List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkedUserInfoList);
        }
        return new Response<>(result);
    }

}
