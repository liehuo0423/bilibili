package com.bilibili.service.impl;

import com.bilibili.mapper.DemoMapper;
import com.bilibili.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Service
public class demoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;
    @Override
    public Long query(Long id) {
        return demoMapper.query(id);
    }
}
