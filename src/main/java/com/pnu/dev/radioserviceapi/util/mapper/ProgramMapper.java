package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.dto.response.ProgramsPageResponse;
import com.pnu.dev.radioserviceapi.mongo.Program;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProgramMapper {

    public ProgramsPageResponse mapMongoProgramsPageToProgramsPageResponse(Page<Program> mongoProgramsPage) {
        return ProgramsPageResponse.builder()
                .pageNumber(mongoProgramsPage.getNumber())
                .totalPages(mongoProgramsPage.getTotalPages())
                .programs(mongoProgramsPage.getContent().stream()
                        .map(this::mapMongoProgramToProgramDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public ProgramDto mapMongoProgramToProgramDto(Program mongoProgram) {
        return ProgramDto.builder()
                .id(mongoProgram.getId())
                .title(mongoProgram.getTitle())
                .description(mongoProgram.getDescription())
                .imageUrl(mongoProgram.getImageUrl())
                .build();
    }

}
