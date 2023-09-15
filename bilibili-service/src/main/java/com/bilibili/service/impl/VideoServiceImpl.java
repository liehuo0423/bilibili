package com.bilibili.service.impl;

import com.bilibili.domain.Video;
import com.bilibili.domain.VideoTag;
import com.bilibili.mapper.VedioMapper;
import com.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VedioMapper videoMapper;

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
}
