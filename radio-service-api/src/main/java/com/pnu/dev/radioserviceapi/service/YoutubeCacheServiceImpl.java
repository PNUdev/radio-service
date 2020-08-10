package com.pnu.dev.radioserviceapi.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Service
public class YoutubeCacheServiceImpl implements YoutubeCacheService {

    private static final Object KEY_PLACEHOLDER = new Object();

    private Cache<Object, VideosCollectionResponse> cache = Caffeine.newBuilder()
            .expireAfterWrite(20, TimeUnit.MINUTES)
            .build();

    @Override
    public VideosCollectionResponse getRecentVideos() {
        return cache.getIfPresent(KEY_PLACEHOLDER);
    }

    @Override
    public void setRecentVideos(VideosCollectionResponse videosCollectionResponse) {

        if (nonNull(getRecentVideos())) {
            return;
        }

        cache.put(KEY_PLACEHOLDER, videosCollectionResponse);
    }

    @Override
    public void clearCache() {
        cache.cleanUp();
    }

}
