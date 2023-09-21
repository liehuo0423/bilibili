package com.bilibili.mapper.repository;

import com.bilibili.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface VideoRepository extends ElasticsearchRepository<Video,Long> {
    Video findByTitleLike(String keyword);
}
