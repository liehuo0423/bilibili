package com.bilibili.mapper;

import com.bilibili.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    Video getVideoById(Long videoId);

    VideoLike getVideoLikeByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    Integer addVideoLike(VideoLike videoLike);

    Integer deleteVideoLike(@Param("videoId") Long videoId,
                         @Param("userId") Long userId);

    Long getVideoLikes(Long videoId);

    void deleteVideoCollection(@Param("videoId") Long videoId,
                               @Param("userId") Long userId);

    Integer addVideoCollection(VideoCollection videoCollection);

    Long getVideoCollections(Long videoId);

    VideoCollection getVideoCollectionByVideoIdAndUserId(@Param("videoId") Long videoId,
                                                         @Param("userId") Long userId);

    VideoCoin getVideoCoinByVideoIdAndUserId(@Param("videoId") Long videoId,
                                             @Param("userId") Long userId);

    void addVideoCoin(VideoCoin videoCoin);

    void updateVideoCoin(VideoCoin videoCoin);

    Long getVideoCoinsAmount(Long videoId);

    void addVideoComment(VideoComment videoComment);

    Integer pageCountVideoComments(Map<String, Object> params);

    List<VideoComment> pageListVideoComments(Map<String, Object> params);

    List<VideoComment> batchGetVideoCommentsByRootIds(@Param("rootIdList") List<Long> parentIdList);

    Video getVideoDetails(Long videoId);

    VideoView getVideoView(Map<String, Object> params);

    void addVideoView(VideoView videoView);

    Integer getVideoViewCounts(Long videoId);

    List<UserPreference> getAllUserPreference();

    List<Video> batchGetVideosByIds(@Param("idList") List<Long> idList);

    List<VideoTag> getVideoTagsByVideoId(Long videoId);

    Integer deleteVideoTags(@Param("tagIdList") List<Long> tagIdList,
                            @Param("videoId") Long videoId);
}
