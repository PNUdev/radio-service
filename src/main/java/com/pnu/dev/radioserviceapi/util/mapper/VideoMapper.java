package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.mongo.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public Video itemVideosResponseToMongoVideo(ItemYoutubeVideosResponse itemVideosResponse) {

        Video video = Video.builder()
                .id(itemVideosResponse.getId())
                .title(itemVideosResponse.getSnippet().getTitle())
                .description(itemVideosResponse.getSnippet().getDescription())
                .imageUrl(itemVideosResponse.getSnippet().getThumbnails().getHigh().getUrl())
                .build();

        return video;
    }
}
