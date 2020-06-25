package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.exception.ServiceException;
import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.repository.VideoRepository;
import com.pnu.dev.radioserviceapi.util.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final YoutubeApiClient youtubeApiClient;

    private final VideoMapper videoMapper;

    private final static String youtubeLinkRegex =
            "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, YoutubeApiClient youtubeApiClient, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.youtubeApiClient = youtubeApiClient;
        this.videoMapper = videoMapper;
    }

    @Override
    public List<Video> getAll() {
        return videoRepository.findAll(Sort.by("priority").ascending());
    }

    @Override
//    @Transactional
    public void deleteById(String id) {
        Video video = findVideoByIdOrThrowException(id);
        changePriorityFor(video.getPriority(), Integer.MAX_VALUE, -1);
        videoRepository.deleteById(id);
    }

    private Video findVideoByIdOrThrowException(String id) {
        return videoRepository.findById(id).orElseThrow(() -> new RadioServiceAdminException("Video not found"));
    }

    @Override
    public Video findVideoOnYoutubeByLink(String link) throws ServiceException {

        Pattern youtubeLinkPattern = Pattern.compile(youtubeLinkRegex);
        Matcher matcher = youtubeLinkPattern.matcher(link);

        if (matcher.find()) {
            String id = matcher.group(1);
            try {
                ItemYoutubeVideosResponse itemYoutubeVideosResponse = youtubeApiClient.getVideoById(id);
                Video video = videoMapper.itemVideosResponseToMongoVideo(itemYoutubeVideosResponse);
                return video;
            } catch (IndexOutOfBoundsException ex) {
                throw new ServiceException("Bad video id");
            }
        }
        throw new ServiceException("Link does not refer to Youtube video");

    }

    @Override
    @Transactional
    public void create(Video video) {

        video = video.toBuilder()
                .priority(1)
                .build();
        changePriorityFor(video.getPriority() - 1, Integer.MAX_VALUE, 1);

        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void update(Video video) {
        Video videoFromDb = findVideoByIdOrThrowException(video.getId());
        if (videoFromDb.getPriority() > video.getPriority()) {
            changePriorityFor(video.getPriority() - 1, videoFromDb.getPriority(), 1);
        }
        if (videoFromDb.getPriority() < video.getPriority()) {
            changePriorityFor(videoFromDb.getPriority(), video.getPriority() + 1, -1);
        }
        videoRepository.save(video);
    }

    private void changePriorityFor(int priorityStart, int priorityEnd, int i) {
        List<Video> videoListToChangePriority = videoRepository.findAllByPriorityBetween(priorityStart, priorityEnd);
        videoListToChangePriority.forEach(v -> v.setPriority(v.getPriority() + i));
        videoRepository.saveAll(videoListToChangePriority);
    }

}
