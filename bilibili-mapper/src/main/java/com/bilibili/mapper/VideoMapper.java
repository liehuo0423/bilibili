package com.bilibili.mapper;

import com.bilibili.domain.Video;
import com.bilibili.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface VideoMapper {
    void addVideos(Video video);

    void batchAddVideoTags(List<VideoTag> tagList);

    Integer pageCountVideos(Map<String, Object> params);

    List<Video> pageListVideos(Map<String, Object> params);
}
