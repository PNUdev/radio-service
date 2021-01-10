package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import com.pnu.dev.radioserviceapi.repository.RecommendedVideoRepository;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Profile("local")
@Service
public class VideoApiServiceImpl implements VideoApiService {

    private final YoutubeApiClient youtubeApiClient;

    private final RecommendedVideoRepository recommendedVideoRepository;

    private final VideoMapper videoMapper;

    private final YoutubeCacheService youtubeCacheService;

    @Autowired
    public VideoApiServiceImpl(YoutubeApiClient youtubeApiClient,
                               RecommendedVideoRepository recommendedVideoRepository,
                               VideoMapper videoMapper,
                               YoutubeCacheService youtubeCacheService) {

        this.youtubeApiClient = youtubeApiClient;
        this.recommendedVideoRepository = recommendedVideoRepository;
        this.videoMapper = videoMapper;
        this.youtubeCacheService = youtubeCacheService;
    }

    @Override
    public PageResponse<RecommendedVideoDto> findRecommended(Pageable pageable) {
        Page<RecommendedVideo> videoPage = recommendedVideoRepository.findAll(pageable);
        return videoMapper.videoPageToRecommendedVideoPageDto(videoPage);
    }

    @Override
    public PageResponse<RecommendedVideoDto> findRecommendedByTitleContains(String query, Pageable pageable) {
        Page<RecommendedVideo> videoPage = recommendedVideoRepository.findAllByTitleContainsIgnoreCase(query, pageable);
        return videoMapper.videoPageToRecommendedVideoPageDto(videoPage);
    }

    @Override
    public VideosCollectionResponse findRecent() {

        VideosCollectionResponse recentVideosCollectionFromCache = youtubeCacheService.getRecentVideos();

        if (nonNull(recentVideosCollectionFromCache)) {
            return recentVideosCollectionFromCache;
        }

        OperationResult<YoutubeSearchResponse> apiResult = youtubeApiClient.findRecentVideos();
        if (apiResult.isError()) {
            throw new RadioServiceApiException(apiResult.getErrorMessage());
        }
        List<VideoDto> videoDtoList = videoMapper.itemSearchResponseListToVideoDtoList(apiResult.getData().getItems());

        List<VideoDto> recent = videoDtoList.parallelStream()
                .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.COMPLETED)
                        || v.getLiveBroadcastContent().equals(LiveBroadcastContent.NONE))
                .collect(Collectors.toList());

        List<VideoDto> streams = videoDtoList.parallelStream()
                .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.UPCOMING)
                        || v.getLiveBroadcastContent().equals(LiveBroadcastContent.LIVE))
                .collect(Collectors.toList());

        VideosCollectionResponse recentVideosCollectionFromYoutube = VideosCollectionResponse.builder()
                .recent(recent)
                .streams(streams)
                .build();

        youtubeCacheService.setRecentVideos(recentVideosCollectionFromYoutube);

        return recentVideosCollectionFromYoutube;
    }

}
