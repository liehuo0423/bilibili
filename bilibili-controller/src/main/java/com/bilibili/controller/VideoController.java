package com.bilibili.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.*;
import com.bilibili.service.ElasticSearchService;
import com.bilibili.service.VideoService;
import com.bilibili.service.impl.ElasticSearchServiceImpl;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    @PostMapping(value = "/videos",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideos(@RequestBody Video video) {
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        elasticSearchService.addVideo(video);
        return Response.success();
    }

    @GetMapping(value = "/videos",produces = {"application/json;charset=UTF-8"})
    public Response<PageResult<Video>> pageListVideo(Integer size, Integer no, String area) {
        PageResult<Video> result = videoService.pageListVideos(size, no, area);
        return new Response<>(result);
    }

    @GetMapping(value = "/video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request, response, url);
    }

    /**
     * 点赞视频
     */
    @PostMapping(value = "/video-likes",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideoLike(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLike(videoId, userId);
        return Response.success();
    }

    /**
     * 取消点赞视频
     */
    @DeleteMapping(value = "/video-likes",produces = {"application/json;charset=UTF-8"})
    public Response<String> deleteVideoLike(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoLike(videoId, userId);
        return Response.success();
    }

    /**
     * 查询视频点赞数量
     */
    @GetMapping(value = "/video-likes",produces = {"application/json;charset=UTF-8"})
    public Response<Map<String, Object>> getVideoLikes(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
        }
        Map<String, Object> result = videoService.getVideoLikes(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 收藏视频
     */
    @PostMapping(value = "/video-collections",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideoCollection(@RequestBody VideoCollection videoCollection) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCollection(videoCollection, userId);
        return Response.success();
    }

    /**
     * 取消收藏视频
     */
    @DeleteMapping(value = "/video-collections",produces = {"application/json;charset=UTF-8"})
    public Response<String> deleteVideoCollection(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoCollection(videoId, userId);
        return Response.success();
    }

    /**
     * 查询视频收藏数量
     */
    @GetMapping(value = "/video-collections",produces = {"application/json;charset=UTF-8"})
    public Response<Map<String, Object>> getVideoCollections(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
        }
        Map<String, Object> result = videoService.getVideoCollections(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 视频投币
     */
    @PostMapping(value = "/video-coins",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideoCoins(@RequestBody VideoCoin videoCoin) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCoins(videoCoin, userId);
        return Response.success();
    }

    /**
     * 查询视频投币数量
     */
    @GetMapping(value = "/video-coins",produces = {"application/json;charset=UTF-8"})
    public Response<Map<String, Object>> getVideoCoins(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
        }
        Map<String, Object> result = videoService.getVideoCoins(videoId, userId);
        return new Response<>(result);
    }

    /**
     * 添加视频评论
     */
    @PostMapping(value = "/video-comments",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideoComment(@RequestBody VideoComment videoComment) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoComment(videoComment, userId);
        return Response.success();
    }

    /**
     * 分页查询视频评论
     */
    @GetMapping(value = "/video-comments",produces = {"application/json;charset=UTF-8"})
    public Response<PageResult<VideoComment>> pageListVideoComments(@RequestParam Integer size,
                                                                    @RequestParam Integer no,
                                                                    @RequestParam Long videoId) {
        PageResult<VideoComment> result = videoService.pageListVideoComments(size, no, videoId);
        return new Response<>(result);
    }

    /**
     * 获取视频详情
     */
    @GetMapping(value = "/video-details",produces = {"application/json;charset=UTF-8"})
    public Response<Map<String, Object>> getVideoDetails(@RequestParam Long videoId) {
        Map<String, Object> result = videoService.getVideoDetails(videoId);
        return new Response<>(result);
    }

    /**
     * 添加视频观看记录
     */
    @PostMapping(value = "/video-views",produces = {"application/json;charset=UTF-8"})
    public Response<String> addVideoView(@RequestBody VideoView videoView,
                                         HttpServletRequest request) {
        Long userId;
        try {
            userId = userSupport.getCurrentUserId();
            videoView.setUserId(userId);
            videoService.addVideoView(videoView, request);
        } catch (Exception e) {
            videoService.addVideoView(videoView, request);
        }
        return Response.success();
    }

    /**
     * 查询视频播放量
     */
    @GetMapping(value = "/video-view-counts",produces = {"application/json;charset=UTF-8"})
    public Response<Integer> getVideoViewCounts(@RequestParam Long videoId) {
        Integer count = videoService.getVideoViewCounts(videoId);
        return new Response<>(count);
    }

    /**
     * 视频内容推荐
     */
    @GetMapping(value = "/recommendations",produces = {"application/json;charset=UTF-8"})
    public Response<List<Video>> recommend() throws TasteException {
        Long userId = userSupport.getCurrentUserId();
        List<Video> list = videoService.recommend(userId);
        return new Response<>(list);
    }

    /**
     * 查询视频标签
     */
    @GetMapping(value = "/video-tags",produces = {"application/json;charset=UTF-8"})
    public Response<List<VideoTag>> getVideoTagsByVideoId(@RequestParam Long videoId) {
        List<VideoTag> list = videoService.getVideoTagsByVideoId(videoId);
        return new Response<>(list);
    }

    /**
     * 删除视频标签
     */
    @DeleteMapping(value = "/video-tags",produces = {"application/json;charset=UTF-8"})
    public Response<String> deleteVideoTags(@RequestBody JSONObject params) {
        String tagIdList = params.getString("tagIdList");
        Long videoId = params.getLong("videoId");
        videoService.deleteVideoTags(JSONArray.parseArray(tagIdList).toJavaList(Long.class), videoId);
        return Response.success();
    }


}
