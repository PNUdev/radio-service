package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleAdminController {

    // 1. update schedule for week (page with 7-day schedule and ability to update it)
    // schedule item should contain time, program and optional description for single occurrence

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleAdminController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public String showDaysList() {
        return "schedule/index";
    }

    @GetMapping("/{dayOfWeek}")
    public String showForDay(@PathVariable("dayOfWeek") String dayOfWeekValue, Model model) {

        Optional<DayOfWeek> dayOfWeek = DayOfWeek.findByValue(dayOfWeekValue);

        if (!dayOfWeek.isPresent()) {
            throw new RadioServiceAdminException("error message");
        }

        DailySchedule dailySchedule = scheduleService.findForDay(dayOfWeek.get());
        model.addAttribute("dayOfWeek", dayOfWeek.get().getHumanReadableName());
        model.addAttribute("dailySchedule", dailySchedule);

        return "schedule/dailySchedule";
    }
}
