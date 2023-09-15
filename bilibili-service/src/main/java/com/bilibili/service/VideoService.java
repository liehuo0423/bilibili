package com.bilibili.service;

import com.bilibili.domain.PageResult;
import com.bilibili.domain.Video;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
}
