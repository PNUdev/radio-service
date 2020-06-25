package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;

import java.util.List;

public interface YoutubeApiClient {

    List<ItemYoutubeSearchResponse> getChannelLastVideos();

    ItemYoutubeVideosResponse getVideoById(String id);

}
