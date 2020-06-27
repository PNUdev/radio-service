package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Video;

import java.util.List;

public interface VideoService {

    List<Video> findAll();

    void deleteById(String id);

    void create(String link);

    void updatePriority(String id, Integer newPriority);

}
