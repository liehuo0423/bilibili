package com.bilibili.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bilibili.domain.PageResult;
import com.bilibili.domain.UserMoment;
import com.bilibili.domain.constant.UserMomentsConstant;
import com.bilibili.mapper.UserMomentsMapper;
import com.bilibili.service.UserMomentsService;
import com.bilibili.utils.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class UserMomentsServiceImpl implements UserMomentsService {
    @Autowired
    private UserMomentsMapper userMomentsMapper;
    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private RedisTemplate<String ,String> redisTemplate;
    @Override
    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsMapper.addUserMoments(userMoment);
        DefaultMQProducer producer =(DefaultMQProducer) applicationContext.getBean("momentsProducer");
        Message message = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,message);
    }

    @Override
    public List<UserMoment> getSubscribedUserMoments(Long userId) {
        String key = "subscribed-" + userId;
        String subscribedList = redisTemplate.opsForValue().get(key);
        List<UserMoment> list = JSONArray.parseArray(subscribedList, UserMoment.class);
        return list;
    }
}
