package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.service.VideoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/videos", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoApiController {


    private final VideoApiService videoApiService;

    @Autowired
    public VideoApiController(VideoApiService videoApiService) {
        this.videoApiService = videoApiService;
    }

    @GetMapping("/recommended")
    public PageResponse<RecommendedVideoDto> findRecommended(@PageableDefault(size = 5, sort = "priority",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return videoApiService.findRecommended(pageable);
    }

    @GetMapping("/recommended/search")
    public PageResponse<RecommendedVideoDto> findRecommendedByTitleContains(@RequestParam("q") String query,
                                                                            @PageableDefault(size = 5,
                                                                                    sort = "priority",
                                                                                    direction = Sort.Direction.ASC)
                                                                                    Pageable pageable) {
        return videoApiService.findRecommendedByTitleContains(query, pageable);
    }

    @GetMapping("/recent")
    public VideosCollectionResponse findRecent() {
        return videoApiService.findRecent();
    }

}
