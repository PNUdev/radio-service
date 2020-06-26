package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoApiService {

    Page<VideoDto> findRecommended(Pageable pageable);

    VideosCollectionResponse findLast();
}
