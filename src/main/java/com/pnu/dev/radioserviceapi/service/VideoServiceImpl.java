package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.YoutubeApiResult;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import com.pnu.dev.radioserviceapi.util.validation.YoutubeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
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
        changePriorityFor(video.getPriority(), Integer.MAX_VALUE, -1);
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

        changePriorityFor(video.getPriority() - 1, Integer.MAX_VALUE, 1);

        videoRepository.save(video);
    }

    private Video findVideoOnYoutube(String link) {

        Matcher matcher = YoutubeValidator.matchYoutubeLink(link);

        if (!matcher.find()) {
            throw new RadioServiceAdminException("Link does not refer to Youtube video");
        }

        String id = matcher.group(1);
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
            throw new RadioServiceAdminException("Priority should be in 1..{Videos number}");
        }

        Video videoFromDb = findVideoByIdOrThrowException(id);

        int previousPriority = videoFromDb.getPriority();

        if (previousPriority != newPriority) {

            Video updatedVideo = videoFromDb
                    .toBuilder()
                    .priority(newPriority)
                    .build();

            if (previousPriority > newPriority) {
                changePriorityFor(newPriority - 1, previousPriority, 1);
            }
            if (previousPriority < newPriority) {
                changePriorityFor(previousPriority, newPriority + 1, -1);
            }
            videoRepository.save(updatedVideo);
        }

    }

    private Video findVideoByIdOrThrowException(String id) {

        return videoRepository.findById(id).orElseThrow(() -> new RadioServiceAdminException("Video not found"));
    }

    private void changePriorityFor(int priorityStart, int priorityEnd, int i) {

        List<Video> videoListToChangePriority = videoRepository.findAllByPriorityBetween(priorityStart, priorityEnd);
        List<Video> updatedVideos = videoListToChangePriority
                .stream()
                .peek(v -> v.setPriority(v.getPriority() + i))
                .collect(Collectors.toList());
        videoRepository.saveAll(updatedVideos);
    }

}
