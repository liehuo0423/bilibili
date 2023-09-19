package com.bilibili.service;

import com.bilibili.domain.Danmu;

import java.text.ParseException;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface DanmuService {
    void addDanmu(Danmu danmu);
    void asyncAddDanmu(Danmu danmu);
    List<Danmu> getDanmus(Long videoId,
                          String startTime, String endTime) throws ParseException;
    void addDanmusToRedis(Danmu danmu);
}
