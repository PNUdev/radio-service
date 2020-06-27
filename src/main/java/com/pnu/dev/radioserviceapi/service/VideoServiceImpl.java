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
        updatePriorityForRange(video.getPriority(), Integer.MAX_VALUE, -1);
        videoRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void create(String link) {

        Video video = findVideoOnYoutube(link);

        video = video
                .toBuilder()
                .priority(1)
                .build();

        updatePriorityForRange(video.getPriority() - 1, Integer.MAX_VALUE, 1);

        videoRepository.save(video);
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
            throw new RadioServiceAdminException("Приорітет повинен бути в межах від 1 до кількості всіх відео");
        }

        Video videoFromDb = findVideoByIdOrThrowException(id);

        int previousPriority = videoFromDb.getPriority();

        if (previousPriority != newPriority) {

            Video updatedVideo = videoFromDb
                    .toBuilder()
                    .priority(newPriority)
                    .build();

            if (previousPriority > newPriority) {
                updatePriorityForRange(newPriority - 1, previousPriority, 1);
            }
            if (previousPriority < newPriority) {
                updatePriorityForRange(previousPriority, newPriority + 1, -1);
            }
            videoRepository.save(updatedVideo);
        }

    }

    private Video findVideoByIdOrThrowException(String id) {

        return videoRepository.findById(id).orElseThrow(() -> new RadioServiceAdminException("Відео не знайдено"));
    }

    private void updatePriorityForRange(int priorityStart, int priorityEnd, int additional) {

        List<Video> videoListToChangePriority = videoRepository.findAllByPriorityBetween(priorityStart, priorityEnd);
        List<Video> updatedVideos = videoListToChangePriority
                .stream()
                .peek(v -> v.setPriority(v.getPriority() + additional))
                .collect(Collectors.toList());
        videoRepository.saveAll(updatedVideos);
    }

}
