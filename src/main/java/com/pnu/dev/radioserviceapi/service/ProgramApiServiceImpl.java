package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.util.mapper.ProgramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProgramApiServiceImpl implements ProgramApiService {

    private ProgramRepository programRepository;

    private ProgramMapper programMapper;

    @Autowired
    public ProgramApiServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
    }

    @Override
    public PageResponse<ProgramDto> findAll(Pageable pageable) {
        Page<Program> mongoProgramsPage = programRepository.findAll(pageable);
        return programMapper.mapMongoProgramsPageToProgramsPageResponse(mongoProgramsPage);
    }

    @Override
    public ProgramDto findById(String id) {
        Program mongoProgram = programRepository.findById(id)
                .orElseThrow(() -> new RadioServiceApiException("Програму не знайдено"));

        return programMapper.mapMongoProgramToProgramDto(mongoProgram);
    }

    @Override
    public PageResponse<ProgramDto> findByTitleContains(String query, Pageable pageable) {
        Page<Program> mongoProgramsPage = programRepository.findAllByTitleContainsIgnoreCase(query, pageable);
        return programMapper.mapMongoProgramsPageToProgramsPageResponse(mongoProgramsPage);
    }

}
