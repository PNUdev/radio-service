package com.pnu.dev.radioserviceapi.dto.response.schedule;

import com.pnu.dev.radioserviceapi.dto.response.DayOfWeek;
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

    private DayOfWeek dayOfWeek;

    private List<ScheduleItemDto> scheduleItems;

}
