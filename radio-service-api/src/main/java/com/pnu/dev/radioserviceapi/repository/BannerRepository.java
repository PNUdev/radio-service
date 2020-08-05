package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.Banner;
import com.pnu.dev.radioserviceapi.mongo.BannerType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BannerRepository extends MongoRepository<Banner, BannerType> {

    List<Banner> findAllByShowTrue();

}
