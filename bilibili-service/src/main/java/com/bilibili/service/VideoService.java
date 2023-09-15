package com.bilibili.service;

import com.bilibili.domain.Video;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface VideoService {
    @Transactional
    public void addVideos(Video video);
}
