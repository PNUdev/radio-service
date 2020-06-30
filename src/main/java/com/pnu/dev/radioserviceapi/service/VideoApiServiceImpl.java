package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.YoutubeApiResult;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoApiServiceImpl implements VideoApiService {

    private final YoutubeApiClient youtubeApiClient;

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    @Autowired
    public VideoApiServiceImpl(YoutubeApiClient youtubeApiClient, VideoRepository videoRepository, VideoMapper videoMapper) {
        this.youtubeApiClient = youtubeApiClient;
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    @Override
    public PageResponse<RecommendedVideoDto> findRecommended(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("priority").ascending());
        Page<Video> videoPage = videoRepository.findAll(pageable);
        return videoMapper.videoPageToRecommendedVideoPageDto(videoPage);
    }

    @Override
    public VideosCollectionResponse findRecent() {

        YoutubeApiResult<YoutubeSearchResponse> apiResult = youtubeApiClient.findRecentVideos();
        if (apiResult.isError()) {
            throw new RadioServiceApiException(apiResult.getErrorMessage());
        }
        List<VideoDto> videoDtoList = videoMapper.itemSearchResponseListToVideoDtoList(apiResult.getData().getItems());
        List<VideoDto> recent =
                videoDtoList
                        .parallelStream()
                        .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.COMPLETED)
                                || v.getLiveBroadcastContent().equals(LiveBroadcastContent.NONE))
                        .collect(Collectors.toList());

        List<VideoDto> streams =
                videoDtoList
                        .parallelStream()
                        .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.UPCOMING)
                                || v.getLiveBroadcastContent().equals(LiveBroadcastContent.LIVE))
                        .collect(Collectors.toList());

        return VideosCollectionResponse.builder()
                .recent(recent)
                .streams(streams)
                .build();
    }
}
