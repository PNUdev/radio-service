package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.client.dto.OperationResult;
import com.pnu.dev.radioserviceapi.client.dto.YoutubeApiResult;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;

public interface YoutubeApiClient {

    YoutubeApiResult<YoutubeSearchResponse> getLastVideos();

    YoutubeApiResult<ItemYoutubeVideosResponse> findVideo(String id);

}
