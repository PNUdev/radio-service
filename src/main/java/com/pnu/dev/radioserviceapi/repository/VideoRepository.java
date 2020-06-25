package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {

    List<Video> findAllByPriorityBetween(int priorityStart, int priorityEnd);

}
