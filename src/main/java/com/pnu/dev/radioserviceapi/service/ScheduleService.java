package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.schedule.WeeklySchedule;

// ToDo probably methods should be split to two separate classes
public interface ScheduleService {

    DailySchedule findForDay(String dayOfWeekValue);

    WeeklySchedule findForWeek();

    ScheduleItemDto findScheduleItemById(String id);

    void createScheduleItem(NewScheduleItemForm newScheduleItemForm);

    void updateScheduleItem(String id, UpdateScheduleItemForm updateScheduleItemForm);

    void deleteScheduleItem(String id);

}
