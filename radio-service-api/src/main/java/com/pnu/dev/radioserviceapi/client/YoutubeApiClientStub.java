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

        YoutubeSearchResponse response = new YoutubeSearchResponse(){{
            setItems(Arrays.asList(
                    new ItemYoutubeSearchResponse(){{
                        setId(
                                new VideoIdYoutubeResponse(){{ setVideoId("ByH9LuSILxU"); }}
                        );
                        setSnippet(
                                new SnippetYoutubeResponse(){{
                                    setTitle("Cats");
                                    setDescription("Lorem ipsum");
                                    setPublishedAt(LocalDateTime.now());
                                    setLiveBroadcastContent("NONE");
                                }}
                        );
                    }},

                    new ItemYoutubeSearchResponse(){{
                        setId(
                                new VideoIdYoutubeResponse(){{ setVideoId("5qap5aO4i9A"); }}
                        );
                        setSnippet(
                                new SnippetYoutubeResponse(){{
                                    setTitle("lofi hip hop");
                                    setDescription("Lorem ipsum");
                                    setPublishedAt(LocalDateTime.now());
                                    setLiveBroadcastContent("LIVE");
                                }}
                        );
                    }},

                    new ItemYoutubeSearchResponse(){{
                        setId(
                                new VideoIdYoutubeResponse(){{ setVideoId("nybtOIxlku8"); }}
                        );
                        setSnippet(
                                new SnippetYoutubeResponse(){{
                                    setTitle("Гімн України");
                                    setDescription("Lorem ipsum");
                                    setPublishedAt(LocalDateTime.now());
                                    setLiveBroadcastContent("NONE");
                                }}
                        );
                    }}

            ));
        }};

        return OperationResult.success(response);
    }

    @Override
    public OperationResult<ItemYoutubeVideosResponse> findVideo(String id) {
        ItemYoutubeVideosResponse response = new ItemYoutubeVideosResponse();

        SnippetYoutubeResponse snippet = new SnippetYoutubeResponse() {{
            setTitle("Гімн України");
            setDescription("lorem ipsum");
            setPublishedAt(LocalDateTime.now());
            setLiveBroadcastContent("NONE");
        }};

        response.setId("nybtOIxlku8");
        response.setSnippet(snippet);

        return OperationResult.success(response);
    }

}
