package com.bilibili.controller;

import com.bilibili.domain.Response;
import com.bilibili.domain.Video;
import com.bilibili.service.DemoService;
import com.bilibili.service.ElasticSearchService;
import com.bilibili.utils.FastDFSUtil;
import org.elasticsearch.action.admin.indices.resolve.ResolveIndexAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;
    @Autowired
    private FastDFSUtil fastDFSUtil;
    @Autowired
    private ElasticSearchService elasticSearchService;
    @GetMapping("{id}")
    public Long query(@PathVariable Long id){
        return demoService.query(id);
    }
    @PostMapping("/slices")
    public void slices(@RequestBody MultipartFile file) throws Exception {
        fastDFSUtil.convertFileToSlices(file);
    }
    @GetMapping(value = "es-videos",produces = {"application/json;charset=UTF-8"})
    public Response<Video> getVideos(@RequestParam String keyword){
        Video videos = elasticSearchService.getVideos(keyword);
        return new Response<>(videos);
    }
}
