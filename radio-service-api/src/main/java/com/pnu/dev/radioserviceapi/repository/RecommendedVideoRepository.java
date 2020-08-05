package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendedVideoRepository extends MongoRepository<RecommendedVideo, String> {

    Page<RecommendedVideo> findAllByTitleContainsIgnoreCase(String query, Pageable pageable);

    List<RecommendedVideo> findAllByPriorityBetween(int priorityStart, int priorityEnd);

    List<RecommendedVideo> findAllByPriorityGreaterThan(int priority);

}
