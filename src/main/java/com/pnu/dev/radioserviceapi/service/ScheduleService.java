package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;

public interface ScheduleService {

    DailySchedule findForDay(DayOfWeek dayOfWeek);

    WeeklySchedule findForWeek();

}
