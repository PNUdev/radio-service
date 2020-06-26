package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Video;

import java.util.List;

public interface VideoService {

    List<Video> findAll();

    void deleteById(String id);

    Video findVideoOnYoutube(String link);

    void create(Video video);

    void update(String id, Video video);

}
