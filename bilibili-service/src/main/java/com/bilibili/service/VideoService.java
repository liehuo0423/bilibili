package com.bilibili.service;

import com.bilibili.domain.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface VideoService {
    @Transactional
    public void addVideos(Video video);

    PageResult<Video> pageListVideos(Integer size, Integer no, String area);

    void viewVideoOnlineBySlices(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String url) throws Exception;

    void addVideoLike(Long videoId, Long userId);

    void deleteVideoLike(Long videoId, Long userId);

    Map<String, Object> getVideoLikes(Long videoId, Long userId);

    void addVideoCollection(VideoCollection videoCollection, Long userId);

    void deleteVideoCollection(Long videoId, Long userId);

    Map<String, Object> getVideoCollections(Long videoId, Long userId);

    void addVideoCoins(VideoCoin videoCoin, Long userId);

    Map<String, Object> getVideoCoins(Long videoId, Long userId);

    void addVideoComment(VideoComment videoComment, Long userId);

    PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId);

    Map<String, Object> getVideoDetails(Long videoId);

    void addVideoView(VideoView videoView, HttpServletRequest request);

    Integer getVideoViewCounts(Long videoId);

    List<Video> recommend(Long userId) throws TasteException;

    List<VideoTag> getVideoTagsByVideoId(Long videoId);

    void deleteVideoTags(List<Long> toJavaList, Long videoId);
}
