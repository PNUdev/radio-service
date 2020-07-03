package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;

import java.util.List;

public interface RecommendedVideoService {

    List<RecommendedVideo> findAll();

    void deleteById(String id);

    void create(String link);

    void updatePriority(String id, Integer newPriority);

}
