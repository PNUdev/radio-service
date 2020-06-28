package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleAdminController {

    // 1. update schedule for week (page with 7-day schedule and ability to update it)
    // schedule item should contain time, program and optional description for single occurrence

    private ScheduleService scheduleService;

    private ProgramRepository programRepository;

    @Autowired
    public ScheduleAdminController(ScheduleService scheduleService, ProgramRepository programRepository) {
        this.scheduleService = scheduleService;
        this.programRepository = programRepository;
    }

    @GetMapping
    public String showDaysList() {
        return "schedule/index";
    }

    @GetMapping("day/{dayOfWeek}")
    public String showForDay(@PathVariable("dayOfWeek") String dayOfWeekValue, Model model) {

        DailySchedule dailySchedule = scheduleService.findForDay(dayOfWeekValue);
        model.addAttribute("dailySchedule", dailySchedule);

        return "schedule/dailySchedule";
    }

    @GetMapping("/item/new")
    public String addItem(Model model) {

        List<Program> programs = programRepository.findAll();
        model.addAttribute("programs", programs);

        return "schedule/newItem";
    }
}
