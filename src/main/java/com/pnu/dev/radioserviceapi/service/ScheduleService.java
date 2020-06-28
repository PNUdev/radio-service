package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.schedule.WeeklySchedule;

public interface ScheduleService {

    DailySchedule findForDay(String dayOfWeekValue);

    WeeklySchedule findForWeek();

}
