package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.VideoItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoItemRepository extends MongoRepository<VideoItem, String> {
}
