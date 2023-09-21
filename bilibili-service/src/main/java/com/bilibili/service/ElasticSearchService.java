package com.bilibili.service;

import com.bilibili.domain.UserInfo;
import com.bilibili.domain.Video;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface ElasticSearchService {
    void addUseInfo(UserInfo userInfo);
    List<Map<String, Object>> getContents(String keyword,
                                          Integer pageNo,
                                          Integer pageSize) throws IOException;
    void addVideo(Video video);
    Video getVideos(String keyword);
    void deleteVideo();
}
