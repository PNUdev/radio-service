package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.dto.response.ProgramsPageResponse;
import org.springframework.data.domain.Pageable;

public interface ProgramApiService {

    ProgramsPageResponse findAll(Pageable pageable);

    ProgramDto findById(String id);

    ProgramsPageResponse searchByTitle(String query, Pageable pageable);

}
