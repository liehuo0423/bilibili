package com.bilibili.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.domain.*;
import com.bilibili.domain.constant.UserConstant;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.mapper.UserMapper;
import com.bilibili.service.UserAuthService;
import com.bilibili.service.UserService;
import com.bilibili.utils.MD5Util;
import com.bilibili.utils.RSAUtil;
import com.bilibili.utils.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterJob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public void addUser(User user) {
        String phone = user.getPhone();

        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }

        if (!isMobile(phone)) {
            throw new ConditionException("手机号格式不正确");
        }

        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("手机号已注册");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String md5Password = MD5Util.sign(rawPassword, salt, "utf8");

        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setPassword(md5Password);
        newUser.setSalt(salt);
        newUser.setCreateTime(now);
        userMapper.addUser(newUser);

        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(newUser.getId());
        userInfo.setGender(UserConstant.GENDER_UNKNOW);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setNick(UserConstant.DEFAULT_NICK + "_" + UUID.randomUUID());
        userInfo.setCreateTime(now);
        userMapper.addUserInfo(userInfo);

        userAuthService.addUserDefaultRole(newUser.getId());
    }

    @Override
    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }

        if (!isMobile(phone)) {
            throw new ConditionException("手机号格式不正确");
        }
        User dbUser = this.getUserByPhone(phone);

        if (dbUser == null) {
            throw new ConditionException("手机号未注册");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String md5Password = dbUser.getPassword();
        String salt = dbUser.getSalt();
        boolean isVerified = MD5Util.verify(rawPassword, md5Password, salt, "utf8");
        if (!isVerified) {
            throw new ConditionException("密码错误");
        }
        Long userId = dbUser.getId();
        return TokenUtil.generateToken(userId);

    }

    @Override
    public User getUserInfo(Long userId) {
        User user = userMapper.getUserById(userId);
        UserInfo userInfo = userMapper.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    @Override
    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userMapper.updateUserInfos(userInfo);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<UserInfo> getUserInfoByUserIds(Set<Long> followingIdSet) {
        return userMapper.getUserInfoByUserIds(followingIdSet);
    }

    @Override
    public List<UserFollowing> getUserFollowings(Long userId) {
        return userMapper.getUserFollowings(userId);
    }

    @Override
    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no - 1) * size);
        params.put("size", size);
        Integer total = userMapper.pageCountUserInfos(params);
        List<UserInfo> list = new ArrayList<>();
        if (total > 0) {
            list = userMapper.pageListUserInfos(params);
        }
        return new PageResult<>(total,list);
    }

    @Override
    public Map<String, Object> loginForDts(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }

        if (!isMobile(phone)) {
            throw new ConditionException("手机号格式不正确");
        }
        User dbUser = this.getUserByPhone(phone);

        if (dbUser == null) {
            throw new ConditionException("手机号未注册");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String md5Password = dbUser.getPassword();
        String salt = dbUser.getSalt();
        boolean isVerified = MD5Util.verify(rawPassword, md5Password, salt, "utf8");
        if (!isVerified) {
            throw new ConditionException("密码错误");
        }
        Long userId = dbUser.getId();
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        userMapper.deleteRefreshToken(refreshToken,userId);
        userMapper.addRefreshToken(refreshToken,userId,new Date());
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken",accessToken);
        result.put("refreshToken",refreshToken);
        return result;
    }

    @Override
    public void logout(String refreshToken, Long userId) {
        userMapper.deleteRefreshToken(refreshToken,userId);
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
       RefreshToken refreshTokenDetail =  userMapper.getRefreshTokenDetail(refreshToken);
       if(refreshTokenDetail == null) throw new ConditionException(555,"token过期");
        Long userId = refreshTokenDetail.getUserId();
        String accessToken = TokenUtil.generateToken(userId);
        return accessToken;
    }

    @Override
    public List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList) {
        return userMapper.batchGetUserInfoByUserIds(userIdList);
    }

    public static boolean isMobile(final String phone) {
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        return pattern.matcher(phone).matches();
    }

    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }
}
