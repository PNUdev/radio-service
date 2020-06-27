package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.YoutubeApiResult;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.YoutubeVideosResponse;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;


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

    private final RestTemplate restTemplate;

    public YoutubeApiClientImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public YoutubeApiResult<YoutubeSearchResponse> getLastVideos() {

        URIBuilder uriRequest;
        try {
            uriRequest = new URIBuilder(GET_VIDEOS_BY_CHANNEL_URI);
        } catch (URISyntaxException e) {
            return YoutubeApiResult.error("Помилка звернення до Youtube API");
        }
        uriRequest.addParameter("key", API_KEY)
                .addParameter("channelId", CHANNEL_ID)
                .addParameter("maxResults", String.valueOf(MAX_RESULTS_NUMBER));
        YoutubeSearchResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), YoutubeSearchResponse.class);
        if (responseEntity != null) {
            return YoutubeApiResult.success(responseEntity);
        } else {
            return YoutubeApiResult.error("Помилка відповіді від Youtube API");
        }
    }

    @Override
    public YoutubeApiResult<ItemYoutubeVideosResponse> findVideo(String id) {
        URIBuilder uriRequest;
        try {
            uriRequest = new URIBuilder(GET_VIDEO_BY_ID_URI);
        } catch (URISyntaxException e) {
            return YoutubeApiResult.error("Помилка звернення до Youtube API");
        }
        uriRequest.addParameter("key", API_KEY)
                .addParameter("id", id);
        YoutubeVideosResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), YoutubeVideosResponse.class);
        if (responseEntity != null && responseEntity.getItems().size() == 1) {
            return YoutubeApiResult.success(responseEntity.getItems().get(0));
        } else {
            return YoutubeApiResult.error("Помилка відповіді від Youtube API");
        }
    }
}


