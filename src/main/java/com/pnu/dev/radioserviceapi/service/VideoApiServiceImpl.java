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
    public Page<VideoDto> findAllRecommended(Pageable pageable) {
        Page<Video> videoPage = videoRepository.findAll(pageable);
        return videoMapper.videoPageToVideoDtoPage(videoPage);
    }

    @Override
    public VideosCollectionResponse findChannelVideos() {
        List<ItemYoutubeSearchResponse> channelVideos = youtubeApiClient.getChannelLastVideos();
        List<ItemYoutubeSearchResponse> recent =
                channelVideos
                        .stream()
                        .filter(v -> v.getSnippet().getLiveBroadcastContent().equalsIgnoreCase(LiveBroadcastContent.COMPLETED.toString())
                                || v.getSnippet().getLiveBroadcastContent().equalsIgnoreCase(LiveBroadcastContent.NONE.toString()))
                        .collect(Collectors.toList());

        List<ItemYoutubeSearchResponse> streams =
                channelVideos
                        .stream()
                        .filter(v -> v.getSnippet().getLiveBroadcastContent().equalsIgnoreCase(LiveBroadcastContent.UPCOMING.toString())
                                || v.getSnippet().getLiveBroadcastContent().equalsIgnoreCase(LiveBroadcastContent.LIVE.toString()))
                        .collect(Collectors.toList());
        VideosCollectionResponse videos = VideosCollectionResponse.builder()
                .recent(videoMapper
                        .itemSearchResponseListToVideoDtoList(recent))
                .streams(videoMapper.itemSearchResponseListToVideoDtoList(streams))
                .build();

        return videos;
    }
}
