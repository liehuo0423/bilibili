package com.bilibili.mapper.repository;

import com.bilibili.domain.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {
}
