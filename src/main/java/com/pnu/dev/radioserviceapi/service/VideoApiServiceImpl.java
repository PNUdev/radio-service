package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<VideoDto> findRecommended(Pageable pageable) {

        Page<Video> videoPage = videoRepository.findAll(pageable);
        return videoMapper.videoPageToVideoDtoPage(videoPage);
    }

    @Override
    public VideosCollectionResponse findLast() {

        List<ItemYoutubeSearchResponse> lastVideos = youtubeApiClient.getLastVideos();
        List<VideoDto> videoDtoList = videoMapper.itemSearchResponseListToVideoDtoList(lastVideos);
        List<VideoDto> recent =
                videoDtoList
                        .stream()
                        .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.COMPLETED)
                                || v.getLiveBroadcastContent().equals(LiveBroadcastContent.NONE))
                        .collect(Collectors.toList());

        List<VideoDto> streams =
                videoDtoList
                        .stream()
                        .filter(v -> v.getLiveBroadcastContent().equals(LiveBroadcastContent.UPCOMING)
                                || v.getLiveBroadcastContent().equals(LiveBroadcastContent.LIVE))
                        .collect(Collectors.toList());

        return VideosCollectionResponse.builder()
                .recent(recent)
                .streams(streams)
                .build();
    }
}
