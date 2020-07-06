package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.util.OperationResult;

import java.util.List;
import java.util.Optional;

public interface ScheduleItemService {

    Optional<ScheduleItemDto> findById(String id);

    List<ScheduleItemDto> findAll();

    List<ScheduleItemDto> findByProgramId(String programId);

    List<ScheduleItemDto> findByDayOfWeek(DayOfWeek dayOfWeek);

    OperationResult<ScheduleItemDto> create(NewScheduleItemForm newScheduleItemForm);

    OperationResult<ScheduleItemDto> update(String id, UpdateScheduleItemForm updateScheduleItemForm);

    void deleteById(String id);

}
