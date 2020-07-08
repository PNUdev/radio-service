package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.dto.response.ScheduleOccurrence;
import com.pnu.dev.radioserviceapi.dto.response.TimeRange;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.service.ScheduleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgramMapper {

    private ScheduleItemService scheduleItemService;

    @Autowired
    public ProgramMapper(ScheduleItemService scheduleItemService) {
        this.scheduleItemService = scheduleItemService;
    }

    public PageResponse<ProgramDto> mapMongoProgramsPageToProgramsPageResponse(Page<Program> mongoProgramsPage) {
        return PageResponse.<ProgramDto>builder()
                .pageNumber(mongoProgramsPage.getNumber())
                .totalPages(mongoProgramsPage.getTotalPages())
                .content(mongoProgramsPage.getContent().stream()
                        .map(this::mapMongoProgramToProgramDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public ProgramDto mapMongoProgramToProgramDto(Program mongoProgram) {

        List<ScheduleItemDto> scheduleItems = scheduleItemService.findByProgramId(mongoProgram.getId());

        return ProgramDto.builder()
                .id(mongoProgram.getId())
                .title(mongoProgram.getTitle())
                .description(mongoProgram.getDescription())
                .scheduleOccurrences(mapScheduleItemsToScheduleOccurrences(scheduleItems))
                .imageUrl(mongoProgram.getImageUrl())
                .build();
    }

    private List<ScheduleOccurrence> mapScheduleItemsToScheduleOccurrences(List<ScheduleItemDto> scheduleItems) {

        return scheduleItems.stream()
                .map(scheduleItem -> ScheduleOccurrence.builder()
                        .dayOfWeek(scheduleItem.getDayOfWeek())
                        .timeRange(TimeRange.builder()
                                .startTime(scheduleItem.getTime().getStartTime())
                                .endTime(scheduleItem.getTime().getEndTime())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

}
