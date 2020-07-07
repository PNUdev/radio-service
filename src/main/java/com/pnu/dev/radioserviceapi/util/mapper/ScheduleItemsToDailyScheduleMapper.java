package com.pnu.dev.radioserviceapi.util.mapper;

import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleItemsToDailyScheduleMapper {

    public DailySchedule map(List<ScheduleItemDto> scheduleItems, DayOfWeek dayOfWeek) {

        return DailySchedule.builder()
                .dayOfWeek(dayOfWeek.toDayOfWeekResponse())
                .scheduleItems(scheduleItems)
                .build();

    }

}
