package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import com.pnu.dev.radioserviceapi.mongo.Video;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoMapper {

    public Video itemVideosResponseToVideo(ItemYoutubeVideosResponse itemVideosResponse) {

        return Video.builder()
                .id(itemVideosResponse.getId())
                .title(itemVideosResponse.getSnippet().getTitle())
                .description(itemVideosResponse.getSnippet().getDescription())
                .build();
    }

    public List<VideoDto> itemSearchResponseListToVideoDtoList(List<ItemYoutubeSearchResponse> youtubeSearchResponse) {

        return youtubeSearchResponse.stream().map(this::itemSearchResponseToVideoDto).collect(Collectors.toList());
    }

    private VideoDto itemSearchResponseToVideoDto(ItemYoutubeSearchResponse itemSearchResponse) {

        return VideoDto.builder()
                .id(itemSearchResponse.getId().getVideoId())
                .title(itemSearchResponse.getSnippet().getTitle())
                .description(itemSearchResponse.getSnippet().getDescription())
                .publishedAt(itemSearchResponse.getSnippet().getPublishedAt())
                .liveBroadcastContent(LiveBroadcastContent
                        .valueOf(itemSearchResponse.getSnippet().getLiveBroadcastContent().toUpperCase()))
                .build();
    }

    public PageResponse<RecommendedVideoDto> videoPageToRecommendedVideoPageDto(Page<Video> videoPage) {

        return new PageResponse<RecommendedVideoDto>().toBuilder()
                .pageNumber(videoPage.getNumber())
                .totalPages(videoPage.getTotalPages())
                .content(videoListToRecommendedVideoDtoList(videoPage.getContent()))
                .build();
    }

    private List<RecommendedVideoDto> videoListToRecommendedVideoDtoList(List<Video> videoList) {

        return videoList.stream().map(this::videoToRecommendedVideoDto).collect(Collectors.toList());
    }


    private RecommendedVideoDto videoToRecommendedVideoDto(Video video) {

        return RecommendedVideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .build();
    }
}
