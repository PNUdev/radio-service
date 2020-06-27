package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.YoutubeApiResult;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import com.pnu.dev.radioserviceapi.util.validation.YoutubeVideoIdExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final YoutubeApiClient youtubeApiClient;

    private final VideoMapper videoMapper;


    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, YoutubeApiClient youtubeApiClient, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.youtubeApiClient = youtubeApiClient;
        this.videoMapper = videoMapper;
    }

    @Override
    public List<Video> findAll() {

        return videoRepository.findAll(Sort.by("priority").ascending());
    }

    @Override
    @Transactional
    public void deleteById(String id) {

        Video video = findVideoByIdOrThrowException(id);

        List<Video> videosToDecrementPriority = videoRepository
                .findAllByPriorityGreaterThan(video.getPriority());

        videoRepository.deleteById(id);

        decrementPriority(videosToDecrementPriority);
    }


    @Override
    @Transactional
    public void create(String link) {

        Video videoFromYoutube = findVideoOnYoutube(link);

        Optional<Video> videoFromDb = videoRepository.findById(videoFromYoutube.getId());

        //Move video to the top if already exists in db
        if (videoFromDb.isPresent()) {
            updatePriority(videoFromDb.get(), 1);
        } else {

            videoFromYoutube = videoFromYoutube
                    .toBuilder()
                    .priority(1)
                    .build();

            List<Video> videosToIncrementPriority = videoRepository
                    .findAll();

            videoRepository.save(videoFromYoutube);
            incrementPriority(videosToIncrementPriority);
        }
    }

    private Video findVideoOnYoutube(String link) {

        Optional<String> optionalId = YoutubeVideoIdExtractor.getVideoIdFromLink(link);
        if (!optionalId.isPresent()) {
            throw new RadioServiceAdminException("Посилання не відповідає Youtube посиланню");
        }

        String id = optionalId.get();
        YoutubeApiResult<ItemYoutubeVideosResponse> apiResult = youtubeApiClient.findVideo(id);

        if (apiResult.isError()) {
            throw new RadioServiceAdminException(apiResult.getErrorMessage());
        }

        ItemYoutubeVideosResponse itemYoutubeVideosResponse = apiResult.getData();
        return videoMapper.itemVideosResponseToVideo(itemYoutubeVideosResponse);
    }

    @Override
    @Transactional
    public void updatePriority(String id, Integer newPriority) {

        long numberOfVideos = videoRepository.count();

        if (newPriority < 1 || newPriority > numberOfVideos) {
            throw new RadioServiceAdminException(
                    String.format("Приорітет повинен бути в межах від 1 до %s (кількість всіх відео)", numberOfVideos));
        }
        Video video = findVideoByIdOrThrowException(id);
        updatePriority(video, newPriority);
    }

    private void updatePriority(Video videoFromDb, Integer newPriority) {

        int previousPriority = videoFromDb.getPriority();

        if (previousPriority != newPriority) {

            Video updatedVideo = videoFromDb
                    .toBuilder()
                    .priority(newPriority)
                    .build();


            if (previousPriority > newPriority) {
                List<Video> videosToIncrementPriority = videoRepository
                        .findAllByPriorityBetween(newPriority - 1, previousPriority);
                incrementPriority(videosToIncrementPriority);
            }
            if (previousPriority < newPriority) {
                List<Video> videosToDecrementPriority = videoRepository
                        .findAllByPriorityBetween(previousPriority, newPriority + 1);
                decrementPriority(videosToDecrementPriority);
            }

            videoRepository.save(updatedVideo);
        }
    }

    private Video findVideoByIdOrThrowException(String id) {

        return videoRepository.findById(id).orElseThrow(() -> new RadioServiceAdminException("Відео не знайдено"));
    }

    private void incrementPriority(List<Video> videoListToIncrementPriority) {

        List<Video> updatedVideos = videoListToIncrementPriority
                .stream()
                .peek(v -> v.setPriority(v.getPriority() + 1))
                .collect(Collectors.toList());
        videoRepository.saveAll(updatedVideos);
    }

    private void decrementPriority(List<Video> videoListToDecrementPriority) {

        List<Video> updatedVideos = videoListToDecrementPriority
                .stream()
                .peek(v -> v.setPriority(v.getPriority() - 1))
                .collect(Collectors.toList());
        videoRepository.saveAll(updatedVideos);
    }
}
