package com.bilibili.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@RestController
@RequestMapping("/objects")
public class RestfulApi {
    private final Map<Integer, Map<String, Object>> dataMap;

    RestfulApi() {
        this.dataMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", i);
            data.put("name", "name" + i);
            dataMap.put(i, data);
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> queryObject(@PathVariable Integer id) {
        return dataMap.get(id);
    }

    @PostMapping
    public String saveObject(@RequestBody Map<String, Object> data) {
        Integer[] integers = dataMap.keySet().toArray(new Integer[dataMap.size()]);
        Arrays.sort(integers);
        int nextInt = integers[integers.length - 1] + 1;
        dataMap.put(nextInt,data);
        return "<h1>Post Success</h1>";
    }

    @DeleteMapping("/{id}")
    public String  deleteObject(@PathVariable Integer id) {
        dataMap.remove(id);
        return "<h1>Delete Success</h1>";
    }

    @PutMapping
    public String updateObject(@RequestBody Map<String, Object> data) {
        int id = Integer.valueOf(String.valueOf(data.get("id")));
        Map<String, Object> containedMap = dataMap.get(id);
        if(containedMap == null){
            Integer[] integers = dataMap.keySet().toArray(new Integer[dataMap.size()]);
            Arrays.sort(integers);
            int nextInt = integers[integers.length - 1] + 1;
            dataMap.put(nextInt,data);
        }else {
            dataMap.put(id,data);
        }
        return "<h1>Put Success</h1>";
    }
}
