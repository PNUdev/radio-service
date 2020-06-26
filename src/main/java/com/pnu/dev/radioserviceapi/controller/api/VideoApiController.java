package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import com.pnu.dev.radioserviceapi.service.VideoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<VideoDto> findRecommended(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                     Pageable pageable) {
        return videoApiService.findRecommended(pageable);
    }

    @GetMapping("/last")
    public VideosCollectionResponse findLast() {
        return videoApiService.findLast();
    }


}
