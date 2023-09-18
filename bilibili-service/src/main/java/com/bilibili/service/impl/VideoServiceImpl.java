package com.bilibili.service.impl;

import com.bilibili.domain.*;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.mapper.VideoMapper;
import com.bilibili.service.VideoService;
import com.bilibili.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Override
    @Transactional
    public void addVideos(Video video) {
        Date now = new Date();
        video.setCreateTime(new Date());
        videoMapper.addVideos(video);
        Long videoId = video.getId();
        List<VideoTag> tagList = video.getVideoTagList();
        tagList.forEach(item -> {
            item.setCreateTime(now);
            item.setVideoId(videoId);
        });
        videoMapper.batchAddVideoTags(tagList);
    }

    @Override
    public PageResult<Video> pageListVideos(Integer size, Integer no, String area) {
        if(size == null || no == null || size == 0 || no == 0){
            throw new ConditionException("参数错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("start",(no-1)*size);
        params.put("limit",size);
        params.put("area",area);
        List<Video> videos = new ArrayList<>();
        Integer total = videoMapper.pageCountVideos(params);
        if(total > 0){
            videos = videoMapper.pageListVideos(params);
        }

        return new PageResult<>(total,videos);
    }

    @Override
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        fastDFSUtil.viewVideoOnlineBySlices(request,response,url);
    }

    @Override
    public void addVideoLike(Long videoId, Long userId) {
        Video video = videoMapper.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        VideoLike videoLike = videoMapper.getVideoLikeByVideoIdAndUserId(videoId, userId);
        if(videoLike != null){
            throw new ConditionException("已经赞过！");
        }
        videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLike.setCreateTime(new Date());
        videoMapper.addVideoLike(videoLike);
    }

    @Override
    public void deleteVideoLike(Long videoId, Long userId) {
        videoMapper.deleteVideoLike(videoId, userId);
    }

    @Override
    public Map<String, Object> getVideoLikes(Long videoId, Long userId) {
        Long count = videoMapper.getVideoLikes(videoId);
        VideoLike videoLike = videoMapper.getVideoLikeByVideoIdAndUserId(videoId, userId);
        boolean like = videoLike != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }

    @Override
    @Transactional
    public void addVideoCollection(VideoCollection videoCollection, Long userId) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null){
            throw new ConditionException("参数异常！");
        }
        Video video = videoMapper.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        //删除原有视频收藏
        videoMapper.deleteVideoCollection(videoId, userId);
        //添加新的视频收藏
        videoCollection.setUserId(userId);
        videoCollection.setCreateTime(new Date());
        videoMapper.addVideoCollection(videoCollection);
    }

    @Override
    public void deleteVideoCollection(Long videoId, Long userId) {
        videoMapper.deleteVideoCollection(videoId, userId);
    }

    @Override
    public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
        Long count = videoMapper.getVideoCollections(videoId);
        VideoCollection videoCollection = videoMapper.getVideoCollectionByVideoIdAndUserId(videoId, userId);
        boolean like = videoCollection != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }
}
