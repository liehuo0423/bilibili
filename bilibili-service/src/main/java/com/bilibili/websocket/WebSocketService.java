package com.bilibili.websocket;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.config.WebSocketConfig;
import com.bilibili.domain.Danmu;
import com.bilibili.domain.constant.UserMomentsConstant;
import com.bilibili.service.DanmuService;
import com.bilibili.utils.RocketMQUtil;
import com.bilibili.utils.TokenUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{token}")
public class WebSocketService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    private ConcurrentMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();
    private Session session;
    private String sessionId;
    private Long userId;
    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token) {
        try {
            this.userId = TokenUtil.verifyToken(token);
        } catch (Exception ignore) {
        }
        this.sessionId = session.getId();
        this.session = session;
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId, this);
        } else {
            WEBSOCKET_MAP.put(sessionId, this);
            ONLINE_COUNT.getAndIncrement();
        }
        log.info("用户连接成功:{},当前在线人数：{}", sessionId, ONLINE_COUNT.get());
        try {
            this.sendMessage("0");
        } catch (Exception e) {
            log.error("连接异常");
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void closeConnection() {
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        log.info("用户退出:{},当前在线人数:{}", sessionId, ONLINE_COUNT.get());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("用户信息：" + sessionId + "，报文：" + message);
        if (!StringUtil.isNullOrEmpty(message)) {
            try {
                //群发消息
                for (Map.Entry<String, WebSocketService> entry : WEBSOCKET_MAP.entrySet()) {
                    WebSocketService webSocketService = entry.getValue();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", message);
                    jsonObject.put("sessionId", webSocketService.getSessionId());
                }
                if (this.userId != null) {
                    //保存弹幕到数据库
                    Danmu danmu = JSONObject.parseObject(message, Danmu.class);
                    danmu.setUserId(userId);
                    danmu.setCreateTime(new Date());
                    DanmuService danmuService = (DanmuService) APPLICATION_CONTEXT.getBean("danmuService");
                    danmuService.asyncAddDanmu(danmu);
                    //保存弹幕到redis
                    danmuService.addDanmusToRedis(danmu);
                }
            } catch (Exception e) {
                logger.error("弹幕接收出现问题");
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable throwable) {

    }

    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }
}
