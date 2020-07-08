package com.pnu.dev.radioserviceapi.dto.response.schedule;

import com.pnu.dev.radioserviceapi.dto.response.DayOfWeekResponse;
import com.pnu.dev.radioserviceapi.dto.response.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItemDto {

    private String id;

    private String programName;

    private String programLink;

    private TimeRange time;

    private String comment;

    private DayOfWeekResponse dayOfWeek;

}
