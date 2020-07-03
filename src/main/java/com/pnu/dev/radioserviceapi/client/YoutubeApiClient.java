package com.pnu.dev.radioserviceapi.client;

import com.pnu.dev.radioserviceapi.util.OperationResult;
import com.pnu.dev.radioserviceapi.client.dto.search.YoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;

public interface YoutubeApiClient {

    OperationResult<YoutubeSearchResponse> findRecentVideos();

    OperationResult<ItemYoutubeVideosResponse> findVideo(String id);

}
