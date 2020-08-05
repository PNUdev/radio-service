package com.pnu.dev.radioserviceapi.dto.response.schedule;

import com.pnu.dev.radioserviceapi.dto.response.DayOfWeekResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySchedule {

    private DayOfWeekResponse dayOfWeek;

    private List<ScheduleItemDto> scheduleItems;

}
