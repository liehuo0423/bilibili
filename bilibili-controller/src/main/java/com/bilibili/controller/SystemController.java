package com.bilibili.controller;

import com.bilibili.domain.Response;
import com.bilibili.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
public class SystemController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping(value = "/contents",produces = {"application/json;charset=UTF-8"})
    public Response<List<Map<String, Object>>> getContents(@RequestParam String keyword,
                                                           @RequestParam Integer pageNo,
                                                           @RequestParam Integer pageSize) throws IOException {
        List<Map<String, Object>> list = elasticSearchService.getContents(keyword,pageNo,pageSize);
        return new Response<>(list);
    }
}
