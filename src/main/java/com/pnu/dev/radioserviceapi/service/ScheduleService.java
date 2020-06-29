package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;

import java.util.List;

// ToDo probably methods should be split to two separate classes
public interface ScheduleService {

    DailySchedule findForDay(String dayOfWeekValue);

    List<ScheduleItemDto> findForProgram(String programId);

    WeeklySchedule findForWeek();

    ScheduleItemDto findScheduleItemById(String id);

    void createScheduleItem(NewScheduleItemForm newScheduleItemForm);

    void updateScheduleItem(String id, UpdateScheduleItemForm updateScheduleItemForm);

    void deleteScheduleItem(String id);

}
