package com.bilibili.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bilibili.domain.UserFollowing;
import com.bilibili.domain.UserMoment;
import com.bilibili.domain.constant.UserMomentsConstant;
import com.bilibili.service.UserFollowingService;
import com.mysql.cj.util.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Configuration
public class RocketMQConfig {
    @Value("${rocketmq.name.server.address}")
    public String nameServerAddr;
    @Resource
    public RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserFollowingService userFollowingService;
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }
    @Bean("momentsConsumers")
    public DefaultMQPushConsumer momentsConsumers() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_DANMUS,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = msgs.get(0);
                if(msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String bodyStr = new String(msg.getBody());
                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr), UserMoment.class);
                Long userId = userMoment.getUserId();
                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
                for (UserFollowing userFan : userFans) {
                    Long userFanId = userFan.getUserId();
                    String key = "subscribed-" + userFanId;
                    String subscribeList = redisTemplate.opsForValue().get(key);
                    List<UserMoment> subscribeUserMoments;
                    if(StringUtils.isNullOrEmpty(subscribeList)){
                        subscribeUserMoments =  new ArrayList<>();
                    }else {
                        subscribeUserMoments = JSONArray.parseArray(subscribeList, UserMoment.class);
                    }
                    subscribeUserMoments.add(userMoment);
                    redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subscribeUserMoments));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}
