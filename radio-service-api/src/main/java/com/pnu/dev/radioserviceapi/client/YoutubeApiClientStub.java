package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.VideoIdYoutubeResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"default", "local"})
@Service
public class YoutubeApiClientStub implements YoutubeApiClient {

    List<YoutubeVideoDetails> videos;

    @AllArgsConstructor
    private class YoutubeVideoDetails {
        private String id;
        private String title;
        private String description;
        private String LiveBroadcastContent;
    }

    private ItemYoutubeSearchResponse buildItemYoutubeSearchResponse(YoutubeVideoDetails video) {

        return ItemYoutubeSearchResponse.builder()
                .id(
                        VideoIdYoutubeResponse.builder()
                                .videoId(video.id).build())
                .snippet(
                        SnippetYoutubeResponse.builder()
                                .title(video.title)
                                .description(video.description)
                                .publishedAt(LocalDateTime.now())
                                .liveBroadcastContent(video.LiveBroadcastContent)
                                .build())
                .build();
    }

    @Override
    public OperationResult<YoutubeSearchResponse> findRecentVideos() {
        YoutubeSearchResponse response = YoutubeSearchResponse.builder()
                .items(
                        videos.stream()
                                .map(this::buildItemYoutubeSearchResponse)
                                .collect(Collectors.toList())
                ).build();

        return OperationResult.success(response);
    }

    @Override
    public OperationResult<ItemYoutubeVideosResponse> findVideo(String id) {
        return null;
    }

    {
        videos = Arrays.asList(
                new YoutubeVideoDetails("ByH9LuSILxU",
                        "cats :3",
                        "Lorem ipsum",
                        "NONE"),

                new YoutubeVideoDetails("5qap5aO4i9A",
                        "lofi hip hop radio",
                        "Beats to study and relax",
                        "LIVE"),

                new YoutubeVideoDetails("nybtOIxlku8",
                        "Гімн України",
                        "Put your hand on your heart, and be proud of who you are",
                        "NONE")
        );
    }
}
