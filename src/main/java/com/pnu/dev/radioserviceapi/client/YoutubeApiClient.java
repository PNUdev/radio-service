package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;

import java.util.List;

public interface YoutubeApiClient {

    List<ItemYoutubeSearchResponse> getLastVideos();

    ItemYoutubeVideosResponse findVideo(String id);

}
