package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.RecommendedVideoDto;
import com.pnu.dev.radioserviceapi.dto.response.VideosCollectionResponse;
import org.springframework.data.domain.Pageable;

public interface VideoApiService {

    PageResponse<RecommendedVideoDto> findRecommended(Pageable pageable);

    PageResponse<RecommendedVideoDto> findRecommendedByTitleContains(String query, Pageable pageable);

    VideosCollectionResponse findRecent();
}
