package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.search.ItemYoutubeSearchResponse;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;

    private YoutubeApiClient youtubeApiClient;

    private VideoMapper videoMapper;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, YoutubeApiClient youtubeApiClient, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.youtubeApiClient = youtubeApiClient;
        this.videoMapper = videoMapper;
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Video findVideoOnYoutubeById(String id) {
        ItemYoutubeVideosResponse itemYoutubeVideosResponse = youtubeApiClient.getVideoById(id);
        Video video = videoMapper.videoItemResponseToMongoVideo(itemYoutubeVideosResponse);
        return video;
    }

    @Override
    public void create(Video video) {

    }

    @Override
    public void update(Video video) {

    }
}
