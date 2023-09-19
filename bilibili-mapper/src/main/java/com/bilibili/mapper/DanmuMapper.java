package com.bilibili.mapper;

import com.bilibili.domain.Danmu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface DanmuMapper {
    Integer addDanmu(Danmu danmu);

    List<Danmu> getDanmus(Map<String,Object> params);
}
