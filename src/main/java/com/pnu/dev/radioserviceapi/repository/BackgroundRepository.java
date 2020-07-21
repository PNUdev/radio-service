package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.BackgroundImageHrefs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BackgroundRepository extends MongoRepository<BackgroundImageHrefs, String> {

}
