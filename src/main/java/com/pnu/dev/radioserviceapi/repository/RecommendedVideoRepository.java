package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendedVideoRepository extends MongoRepository<RecommendedVideo, String> {

    List<RecommendedVideo> findAllByPriorityBetween(int priorityStart, int priorityEnd);

    List<RecommendedVideo> findAllByPriorityGreaterThan(int priority);

}
