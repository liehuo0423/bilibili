package com.bilibili.mapper;

import com.bilibili.domain.Video;
import com.bilibili.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface VedioMapper {
    void addVideos(Video video);

    void batchAddVideoTags(List<VideoTag> tagList);
}
