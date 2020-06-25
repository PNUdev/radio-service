package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Video;

import java.util.List;

public interface VideoService {

    List<Video> getAll();

    void deleteById(String id);

    Video findVideoOnYoutubeByLink(String link);

    void create(Video video);

    void update(Video video);

}
