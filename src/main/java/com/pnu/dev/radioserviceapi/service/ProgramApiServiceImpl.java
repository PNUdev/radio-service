package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.dto.response.ProgramsPageResponse;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProgramApiServiceImpl implements ProgramApiService {

    private ProgramRepository programRepository;

    @Autowired
    public ProgramApiServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public ProgramsPageResponse findAll(Pageable pageable) {
        Page<Program> mongoProgramsPage = programRepository.findAll(pageable);
        return mapMongoProgramsPageToProgramsPageResponse(mongoProgramsPage);
    }

    @Override
    public ProgramDto findById(String id) {
        Program mongoProgram = programRepository.findById(id)
                .orElseThrow(() -> new RadioServiceApiException("Програму не знайдено"));

        return mapMongoProgramToProgramDto(mongoProgram);
    }

    @Override
    public ProgramsPageResponse searchByTitle(String query, Pageable pageable) {
        Page<Program> mongoProgramsPage = programRepository.findAllByTitleContains(query, pageable);
        return mapMongoProgramsPageToProgramsPageResponse(mongoProgramsPage);
    }

    private ProgramsPageResponse mapMongoProgramsPageToProgramsPageResponse(Page<Program> mongoProgramsPage) {
        return ProgramsPageResponse.builder()
                .pageNumber(mongoProgramsPage.getNumber())
                .totalPages(mongoProgramsPage.getTotalPages())
                .programs(mongoProgramsPage.getContent().stream()
                        .map(this::mapMongoProgramToProgramDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private ProgramDto mapMongoProgramToProgramDto(Program mongoProgram) {
        return ProgramDto.builder()
                .id(mongoProgram.getId())
                .title(mongoProgram.getTitle())
                .description(mongoProgram.getDescription())
                .imageUrl(mongoProgram.getImageUrl())
                .build();
    }
}
