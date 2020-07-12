package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecommendedVideoService {

    Page<RecommendedVideo> findAll(Pageable pageable);

    Page<RecommendedVideo> findAllByTitleContains(String query, Pageable pageable);

    RecommendedVideo findById(String id);

    void deleteById(String id);

    void create(String link);

    void updatePriority(String id, Integer newPriority);

    long getVideosNumber();

}
