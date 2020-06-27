package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.VideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import org.springframework.data.domain.Pageable;

public interface VideoApiService {

    PageResponse<VideoDto> findRecommended(Pageable pageable);

    VideosCollectionResponse findLast();
}
