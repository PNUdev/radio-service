package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.YoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Profile("default")
@Component
@Data
public class YoutubeApiClientImpl implements YoutubeApiClient {

    private static final String GET_VIDEOS_BY_CHANNEL_URI =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video";

    private static final String GET_VIDEO_BY_ID_URI =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet";


    @Value("${youtube.api_key}")
    private String API_KEY;

    @Value("${youtube.channel_id}")
    private String CHANNEL_ID;

    @Value("${youtube.max_results_number}")
    private int MAX_RESULTS_NUMBER;

    private static final String YOUTUBE_API_ERROR = "Помилка звернення до Youtube API";

    private final RestTemplate restTemplate;

    @Autowired
    public YoutubeApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OperationResult<YoutubeSearchResponse> findRecentVideos() {

        UriComponents uriRequest;

        try {
            uriRequest = UriComponentsBuilder
                    .fromHttpUrl(GET_VIDEOS_BY_CHANNEL_URI)
                    .queryParam("key", API_KEY)
                    .queryParam("channelId", CHANNEL_ID)
                    .queryParam("maxResults", MAX_RESULTS_NUMBER)
                    .build();
            YoutubeSearchResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), YoutubeSearchResponse.class);
            return OperationResult.success(responseEntity);

        } catch (Exception e) {
            return OperationResult.error(YOUTUBE_API_ERROR);
        }

    }

    @Override
    public OperationResult<ItemYoutubeVideosResponse> findVideo(String id) {

        try {
            UriComponents uriRequest = UriComponentsBuilder
                    .fromHttpUrl(GET_VIDEO_BY_ID_URI)
                    .queryParam("key", API_KEY)
                    .queryParam("id", id)
                    .build();
            YoutubeVideosResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), YoutubeVideosResponse.class);

            if (responseEntity == null || responseEntity.getItems().isEmpty()) {
                return OperationResult.error(YOUTUBE_API_ERROR);
            }
            return OperationResult.success(responseEntity.getItems().get(0));

        } catch (Exception e) {
            return OperationResult.error(YOUTUBE_API_ERROR);
        }

    }

}


