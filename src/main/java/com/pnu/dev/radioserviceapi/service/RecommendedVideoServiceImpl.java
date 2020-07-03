package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import com.pnu.dev.radioserviceapi.client.dto.videos.ItemYoutubeVideosResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import com.pnu.dev.radioserviceapi.repository.RecommendedVideoRepository;
import com.pnu.dev.radioserviceapi.util.OperationResult;
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
public class RecommendedVideoServiceImpl implements RecommendedVideoService {

    private final RecommendedVideoRepository recommendedVideoRepository;

    private final YoutubeApiClient youtubeApiClient;

    private final VideoMapper videoMapper;


    @Autowired
    public RecommendedVideoServiceImpl(RecommendedVideoRepository recommendedVideoRepository,
                                       YoutubeApiClient youtubeApiClient, VideoMapper videoMapper) {
        this.recommendedVideoRepository = recommendedVideoRepository;
        this.youtubeApiClient = youtubeApiClient;
        this.videoMapper = videoMapper;

    }

    @Override
    public List<RecommendedVideo> findAll() {

        return recommendedVideoRepository.findAll(Sort.by("priority").ascending());

    }

    @Override
    @Transactional
    public void create(String link) {

        RecommendedVideo videoFromYoutube = findVideoOnYoutube(link);
        Optional<RecommendedVideo> recommendedVideoFromDb = recommendedVideoRepository.findById(videoFromYoutube.getId());

        //Move video to the top if already exists in db
        if (recommendedVideoFromDb.isPresent()) {

            updatePriority(recommendedVideoFromDb.get(), 1);

        } else {

            videoFromYoutube = videoFromYoutube
                    .toBuilder()
                    .priority(1)
                    .build();

            List<RecommendedVideo> videosToIncrementPriority = recommendedVideoRepository
                    .findAll();

            recommendedVideoRepository.save(videoFromYoutube);
            incrementPriority(videosToIncrementPriority);

        }

    }

    @Override
    @Transactional
    public void deleteById(String id) {

        RecommendedVideo recommendedVideo = findRecommendedVideoByIdOrThrowException(id);

        List<RecommendedVideo> videosToDecrementPriority = recommendedVideoRepository
                .findAllByPriorityGreaterThan(recommendedVideo.getPriority());

        recommendedVideoRepository.deleteById(id);
        decrementPriority(videosToDecrementPriority);

    }

    private RecommendedVideo findVideoOnYoutube(String link) {

        String id = YoutubeVideoIdExtractor.getVideoIdFromLink(link).orElseThrow(() -> {
            throw new RadioServiceAdminException("Посилання не відповідає Youtube посиланню");
        });

        OperationResult<ItemYoutubeVideosResponse> apiResult = youtubeApiClient.findVideo(id);

        if (apiResult.isError()) {
            throw new RadioServiceAdminException(apiResult.getErrorMessage());
        }

        ItemYoutubeVideosResponse itemYoutubeVideosResponse = apiResult.getData();
        return videoMapper.itemVideosResponseToRecommendedVideo(itemYoutubeVideosResponse);

    }

    @Override
    @Transactional
    public void updatePriority(String id, Integer newPriority) {

        long numberOfVideos = recommendedVideoRepository.count();

        if (newPriority < 1 || newPriority > numberOfVideos) {
            throw new RadioServiceAdminException(
                    String.format("Приорітет повинен бути в межах від 1 до %s (кількість всіх відео)", numberOfVideos));
        }
        RecommendedVideo recommendedVideo = findRecommendedVideoByIdOrThrowException(id);
        updatePriority(recommendedVideo, newPriority);
    }

    private void updatePriority(RecommendedVideo recommendedVideo, Integer newPriority) {

        int previousPriority = recommendedVideo.getPriority();

        if (previousPriority == newPriority) {
            return;
        }

        RecommendedVideo updatedRecommendedVideo = recommendedVideo
                .toBuilder()
                .priority(newPriority)
                .build();


        if (previousPriority > newPriority) {
            List<RecommendedVideo> videosToIncrementPriority = recommendedVideoRepository
                    .findAllByPriorityBetween(newPriority - 1, previousPriority);
            incrementPriority(videosToIncrementPriority);
        }
        if (previousPriority < newPriority) {
            List<RecommendedVideo> videosToDecrementPriority = recommendedVideoRepository
                    .findAllByPriorityBetween(previousPriority, newPriority + 1);
            decrementPriority(videosToDecrementPriority);
        }

        recommendedVideoRepository.save(updatedRecommendedVideo);

    }

    private RecommendedVideo findRecommendedVideoByIdOrThrowException(String id) {

        return recommendedVideoRepository.findById(id).orElseThrow(() -> new RadioServiceAdminException("Відео не знайдено"));
    }

    private void incrementPriority(List<RecommendedVideo> videoListToIncrementPriority) {

        List<RecommendedVideo> updatedRecommendedVideos = videoListToIncrementPriority.stream()
                .map(v -> v.toBuilder().
                        priority(v.getPriority() + 1)
                        .build()
                )
                .collect(Collectors.toList());

        recommendedVideoRepository.saveAll(updatedRecommendedVideos);

    }

    private void decrementPriority(List<RecommendedVideo> videoListToDecrementPriority) {

        List<RecommendedVideo> updatedRecommendedVideos = videoListToDecrementPriority.stream()
                .map(v -> v.toBuilder().
                        priority(v.getPriority() - 1).
                        build()
                )
                .collect(Collectors.toList());

        recommendedVideoRepository.saveAll(updatedRecommendedVideos);

    }
}
