package com.pnu.dev.radioserviceapi.dto.schedule;

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

    private String dayOfWeekName;

    private List<ScheduleItemDto> scheduleItems;

}
