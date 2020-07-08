package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;

import java.util.Optional;

public interface ScheduleService {

    Optional<DailySchedule> findForDay(String dayOfWeekValue);

    DailySchedule findForToday();

    WeeklySchedule findForWeek();

}
