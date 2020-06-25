package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.VideoItemResponse;
import com.pnu.dev.radioserviceapi.client.dto.VideoPageResponse;
import com.pnu.dev.radioserviceapi.exception.ServiceException;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;


@Component
public class YoutubeApiClientImpl implements YoutubeApiClient {

    private static final String GET_VIDEOS_BY_CHANNEL_URI =
            "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&type=video";

    private static final String GET_VIDEO_BY_ID_URI =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&type=video";


    @Value("${youtube.api_key}")
    private String api_key;

    @Value("${youtube.channel_id}")
    private String CHANNEL_ID;

    @Value("${youtube.max_results_number}")
    private int MAX_RESULTS_NUMBER;

    private final RestTemplate restTemplate;

    public YoutubeApiClientImpl() {
        this.restTemplate = new RestTemplate();
    }


    @Override
    public List<VideoItemResponse> getChannelLastVideos() {
        URIBuilder uriRequest;
        try {
            uriRequest = new URIBuilder(GET_VIDEOS_BY_CHANNEL_URI);
        } catch (URISyntaxException e) {
            throw new ServiceException("Bad uri Youtube.api");
        }
        uriRequest.addParameter("key", api_key)
                .addParameter("channelId", CHANNEL_ID)
                .addParameter("maxResults", String.valueOf(MAX_RESULTS_NUMBER));
        VideoPageResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), VideoPageResponse.class);
        if (responseEntity != null) {
            return responseEntity.getItems();
        } else {
            throw new ServiceException("Youtube api response error");
        }
    }

    @Override
    public VideoItemResponse getVideoById(String id) {
        URIBuilder uriRequest;
        try {
            uriRequest = new URIBuilder(GET_VIDEO_BY_ID_URI);
        } catch (URISyntaxException e) {
            throw new ServiceException("Bad uri Youtube.api");
        }
        uriRequest.addParameter("key", api_key)
                .addParameter("id", id);
        VideoPageResponse responseEntity = restTemplate.getForObject(uriRequest.toString(), VideoPageResponse.class);
        if (responseEntity != null) {
            return responseEntity.getItems().get(0);
        } else {
            throw new ServiceException("Youtube api response error");
        }
    }
}


