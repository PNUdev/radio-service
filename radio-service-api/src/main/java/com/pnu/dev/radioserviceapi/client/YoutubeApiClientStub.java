package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.VideoIdYoutubeResponse;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Profile("local")
@Service
public class YoutubeApiClientStub implements YoutubeApiClient {

    @Override
    public OperationResult<YoutubeSearchResponse> findRecentVideos() {

        YoutubeSearchResponse response = YoutubeSearchResponse.builder()
                .items(Arrays.asList(
                        ItemYoutubeSearchResponse.builder()
                            .id(VideoIdYoutubeResponse.builder().videoId("ByH9LuSILxU").build())
                            .snippet(SnippetYoutubeResponse.builder()
                                    .title("Cats")
                                    .description("lorem ipsum")
                                    .publishedAt(LocalDateTime.now())
                                    .liveBroadcastContent("NONE")
                                    .build())
                            .build(),

                        ItemYoutubeSearchResponse.builder()
                                .id(VideoIdYoutubeResponse.builder().videoId("5qap5aO4i9A").build())
                                .snippet(SnippetYoutubeResponse.builder()
                                        .title("lofi hip hop")
                                        .description("lorem ipsum")
                                        .publishedAt(LocalDateTime.now())
                                        .liveBroadcastContent("NONE")
                                        .build())
                                .build(),

                        ItemYoutubeSearchResponse.builder()
                                .id(VideoIdYoutubeResponse.builder().videoId("nybtOIxlku8").build())
                                .snippet(SnippetYoutubeResponse.builder()
                                        .title("Гімн України")
                                        .description("lorem ipsum")
                                        .publishedAt(LocalDateTime.now())
                                        .liveBroadcastContent("NONE")
                                        .build())
                                .build()
                )).build();

        return OperationResult.success(response);
    }

    @Override
    public OperationResult<ItemYoutubeVideosResponse> findVideo(String id) {
        ItemYoutubeVideosResponse response = ItemYoutubeVideosResponse.builder()
                .id("nybtOIxlku8")
                .snippet(SnippetYoutubeResponse.builder()
                        .title("Гімн України")
                        .description("lorem ipsum")
                        .publishedAt(LocalDateTime.now())
                        .liveBroadcastContent("NONE")
                        .build())
                .build();

        return OperationResult.success(response);
    }

}
