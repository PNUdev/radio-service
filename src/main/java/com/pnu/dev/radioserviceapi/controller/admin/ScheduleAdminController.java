package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addItem(Model model, @RequestParam("day") String dayOfWeek) {

        List<Program> programs = programRepository.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("dayOfWeek", dayOfWeek);

        return "schedule/newItem";
    }

    @GetMapping("/item/delete/{id}")
    public String deleteConfirmation(Model model, @RequestParam("day") String dayOfWeek) {

        model.addAttribute("dayOfWeek", dayOfWeek);

        return "schedule/deleteConfirmation"; // ToDo merge deleteConfirmations into one shared template
    }

    @PostMapping("/item/new")
    public String create(@ModelAttribute NewScheduleItemForm newScheduleItemForm) {

        scheduleService.createScheduleItem(newScheduleItemForm);

        return "redirect:/admin/schedule/day/" + newScheduleItemForm.getDayOfWeek();
    }

    @PostMapping("/item/update/{id}")
    public String update(@PathVariable("id") String id,
                         @RequestParam("day") String dayOrWeek,
                         @ModelAttribute UpdateScheduleItemForm updateScheduleItemForm) {

        scheduleService.updateScheduleItem(id, updateScheduleItemForm);

        return String.format("redirect:/admin/schedule/day/%s#%s", dayOrWeek, id);
    }

    @PostMapping("/item/delete/{id}")
    public String delete(@PathVariable("id") String id, @RequestParam("day") String dayOrWeek) {

        scheduleService.deleteScheduleItem(id);

        return "redirect:/admin/schedule/day/" + dayOrWeek;
    }
}
