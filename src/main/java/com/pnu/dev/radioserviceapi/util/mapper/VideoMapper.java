package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import com.pnu.dev.radioserviceapi.mongo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoMapper {

    public Video itemVideosResponseToMongoVideo(ItemYoutubeVideosResponse itemVideosResponse) {

        Video video = Video.builder()
                .id(itemVideosResponse.getId())
                .title(itemVideosResponse.getSnippet().getTitle())
                .description(itemVideosResponse.getSnippet().getDescription())
                .build();
        return video;
    }

    public List<VideoDto> itemSearchResponseListToVideoDtoList(List<ItemYoutubeSearchResponse> youtubeSearchResponse) {
        return youtubeSearchResponse.stream().map(this::itemSearchResponseToVideoDto).collect(Collectors.toList());
    }

    private VideoDto itemSearchResponseToVideoDto(ItemYoutubeSearchResponse itemSearchResponse) {
        VideoDto videoDto = VideoDto.builder()
                .id(itemSearchResponse.getId().getVideoId())
                .title(itemSearchResponse.getSnippet().getTitle())
                .description(itemSearchResponse.getSnippet().getDescription())
                .publishedAt(itemSearchResponse.getSnippet().getPublishedAt())
                .liveBroadcastContent(LiveBroadcastContent
                        .valueOf(itemSearchResponse.getSnippet().getLiveBroadcastContent().toUpperCase()))
                .build();
        return videoDto;
    }

    public Page<VideoDto> videoPageToVideoDtoPage(Page<Video> videoPage) {
        return new PageImpl<>(videoListToVideoDtoList(videoPage.getContent()));
    }

    private List<VideoDto> videoListToVideoDtoList(List<Video> videoList) {
        return videoList.stream().map(this::videoToDto).collect(Collectors.toList());
    }

    private VideoDto videoToDto(Video video) {
        VideoDto videoDto = VideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .build();
        return videoDto;
    }
}
