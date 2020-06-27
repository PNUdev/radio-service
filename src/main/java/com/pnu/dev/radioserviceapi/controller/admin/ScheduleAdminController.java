package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String show(Model model) {

        WeeklySchedule weeklySchedule = scheduleService.findForWeek();
        model.addAttribute("schedule", weeklySchedule);

        return "schedule/index";
    }
}
