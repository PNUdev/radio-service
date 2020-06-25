package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.VideoItemResponse;

import java.util.List;

public interface YoutubeApiClient {

    List<VideoItemResponse> getChannelLastVideos();

    VideoItemResponse getVideoById(String id);

}
