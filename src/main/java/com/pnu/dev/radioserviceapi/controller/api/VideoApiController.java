package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.service.VideoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoApiController {


    private final VideoApiService videoApiService;

    @Autowired
    public VideoApiController(VideoApiService videoApiService) {
        this.videoApiService = videoApiService;
    }

    @GetMapping("/recommended")
    public PageResponse<RecommendedVideoDto> findRecommended(@PageableDefault(size = 10)
                                                                     Pageable pageable) {
        return videoApiService.findRecommended(pageable);
    }

    @GetMapping("/recent")
    public VideosCollectionResponse findRecent() {
        return videoApiService.findRecent();
    }


}
