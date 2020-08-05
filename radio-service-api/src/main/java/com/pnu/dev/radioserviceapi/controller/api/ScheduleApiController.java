package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleApiController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleApiController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/today")
    public DailySchedule findForToday() {
        return scheduleService.findForToday();
    }

    @GetMapping("/week")
    public WeeklySchedule findForWeek() {
        return scheduleService.findForWeek();
    }

}
