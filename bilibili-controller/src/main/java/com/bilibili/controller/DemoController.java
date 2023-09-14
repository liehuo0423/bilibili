package com.bilibili.controller;

import com.bilibili.service.DemoService;
import com.bilibili.utils.FastDFSUtil;
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
    @GetMapping("{id}")
    public Long query(@PathVariable Long id){
        return demoService.query(id);
    }
    @PostMapping("/slices")
    public void slices(@RequestBody MultipartFile file) throws Exception {
        fastDFSUtil.convertFileToSlices(file);
    }
}
