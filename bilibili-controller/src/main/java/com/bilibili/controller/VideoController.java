package com.bilibili.controller;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
}
