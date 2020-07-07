package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.exception.InternalServiceError;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleItemToScheduleItemDtoMapper {

    private ProgramRepository programRepository;

    @Autowired
    public ScheduleItemToScheduleItemDtoMapper(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<ScheduleItemDto> map(List<ScheduleItem> scheduleItems) {
        return scheduleItems.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public ScheduleItemDto map(ScheduleItem scheduleItem) {

        Program program = programRepository.findById(scheduleItem.getProgramId())
                .orElseThrow(() -> new InternalServiceError("Неузгодженість даних: програми не існує"));

        return ScheduleItemDto.builder()
                .id(scheduleItem.getId())
                .programName(program.getTitle())
                .programLink("/api/v1/programs/" + program.getId())
                .comment(scheduleItem.getComment())
                .time(com.pnu.dev.radioserviceapi.dto.response.TimeRange.builder()
                        .startTime(scheduleItem.getStartTime())
                        .endTime(scheduleItem.getEndTime())
                        .build())
                .dayOfWeek(scheduleItem.getDayOfWeek().toDayOfWeekResponse())
                .build();
    }

}
