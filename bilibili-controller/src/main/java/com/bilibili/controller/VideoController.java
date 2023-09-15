package com.bilibili.controller;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.PageResult;
import com.bilibili.domain.Response;
import com.bilibili.domain.Video;
import com.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
public class VideoController {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private VideoService videoService;
    @PostMapping("/videos")
    public Response<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return Response.success();
    }

    @GetMapping("/videos")
    public Response<PageResult<Video>> pageListVideo(Integer size,Integer no,String area){
       PageResult<Video> result = videoService.pageListVideos(size,no,area);
       return new Response<>(result);
    }

    @GetMapping("video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request,response,url);
    }
}
