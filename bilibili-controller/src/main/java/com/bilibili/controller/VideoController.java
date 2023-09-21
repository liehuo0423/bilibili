package com.bilibili.controller;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.*;
import com.bilibili.service.ElasticSearchService;
import com.bilibili.service.VideoService;
import com.bilibili.service.impl.ElasticSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private ElasticSearchService elasticSearchService;
    @PostMapping("/videos")
    public Response<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        elasticSearchService.addVideo(video);
        return Response.success();
    }

    @GetMapping("/videos")
    public Response<PageResult<Video>> pageListVideo(Integer size,Integer no,String area){
       PageResult<Video> result = videoService.pageListVideos(size,no,area);
       return new Response<>(result);
    }

    @GetMapping("/video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request,response,url);
    }

    /**
     * 点赞视频
     */
    @PostMapping("/video-likes")
    public Response<String> addVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLike(videoId, userId);
        return Response.success();
    }

    /**
     * 取消点赞视频
     */
    @DeleteMapping("/video-likes")
    public Response<String> deleteVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoLike(videoId, userId);
        return Response.success();
    }

    /**
     * 查询视频点赞数量
     */
    @GetMapping("/video-likes")
    public Response<Map<String, Object>> getVideoLikes(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = videoService.getVideoLikes(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 收藏视频
     */
    @PostMapping("/video-collections")
    public Response<String> addVideoCollection(@RequestBody VideoCollection videoCollection){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCollection(videoCollection, userId);
        return Response.success();
    }

    /**
     * 取消收藏视频
     */
    @DeleteMapping("/video-collections")
    public Response<String> deleteVideoCollection(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoCollection(videoId, userId);
        return Response.success();
    }

    /**
     * 查询视频收藏数量
     */
    @GetMapping("/video-collections")
    public Response<Map<String, Object>> getVideoCollections(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = videoService.getVideoCollections(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 视频投币
     */
    @PostMapping("/video-coins")
    public Response<String> addVideoCoins(@RequestBody VideoCoin videoCoin){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCoins(videoCoin, userId);
        return Response.success();
    }

    /**
     * 查询视频投币数量
     */
    @GetMapping("/video-coins")
    public Response<Map<String, Object>> getVideoCoins(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = videoService.getVideoCoins(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 添加视频评论
     */
    @PostMapping("/video-comments")
    public Response<String> addVideoComment(@RequestBody VideoComment videoComment){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoComment(videoComment, userId);
        return Response.success();
    }

    /**
     * 分页查询视频评论
     */
    @GetMapping("/video-comments")
    public Response<PageResult<VideoComment>> pageListVideoComments(@RequestParam Integer size,
                                                                        @RequestParam Integer no,
                                                                        @RequestParam Long videoId){
        PageResult<VideoComment> result = videoService.pageListVideoComments(size, no, videoId);
        return new Response<>(result);
    }

    /**
     * 获取视频详情
     */
    @GetMapping("/video-details")
    public Response<Map<String, Object>> getVideoDetails(@RequestParam Long videoId){
        Map<String, Object> result = videoService.getVideoDetails(videoId);
        return new Response<>(result);
    }



}
