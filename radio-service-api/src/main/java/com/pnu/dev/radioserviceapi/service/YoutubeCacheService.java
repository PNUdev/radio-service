package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;

public interface YoutubeCacheService {

    VideosCollectionResponse getRecentVideos();

    void setRecentVideos(VideosCollectionResponse videosCollectionResponse);

    void clearCache();

}
