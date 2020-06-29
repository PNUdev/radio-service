package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import org.springframework.data.domain.Pageable;

public interface ProgramApiService {

    PageResponse findAll(Pageable pageable);

    ProgramDto findById(String id);

    PageResponse findByTitleContains(String query, Pageable pageable);

}
