package com.pnu.dev.radioserviceapi.dto.response.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklySchedule {

    private DailySchedule sunday;

    private DailySchedule monday;

    private DailySchedule tuesday;

    private DailySchedule wednesday;

    private DailySchedule thursday;

    private DailySchedule friday;

    private DailySchedule saturday;

}
