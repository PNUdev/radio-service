package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.DayOfWeekResponse;
import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.service.ProgramService;
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

    private ProgramService programService;

    @Autowired
    public ScheduleAdminController(ScheduleService scheduleService, ProgramService programService) {
        this.scheduleService = scheduleService;
        this.programService = programService;
    }

    @GetMapping
    public String showDaysList() {
        return "schedule/index";
    }

    @GetMapping("day/{dayOfWeek}") // ToDo handle anchor somehow to not to make url weird
    public String showForDay(@PathVariable("dayOfWeek") String dayOfWeekValue, Model model) {

        DailySchedule dailySchedule = scheduleService.findForDay(dayOfWeekValue);
        model.addAttribute("dailySchedule", dailySchedule);

        return "schedule/dailySchedule";
    }

    @GetMapping("/program/{programId}")
    public String showForProgram(@PathVariable("programId") String programId, Model model) {

        Program program = programService.findById(programId);
        model.addAttribute("programName", program.getTitle());

        List<ScheduleItemDto> scheduleItems = scheduleService.findForProgram(programId);
        model.addAttribute("scheduleItems", scheduleItems);

        return "schedule/programOccurrences";
    }

    @GetMapping("/item/new")
    public String addItem(Model model, @RequestParam("day") String dayOfWeekUrlValue) {

        List<Program> programs = programService.findAll();
        model.addAttribute("programs", programs);

        DayOfWeekResponse dayOfWeek = DayOfWeek.findByUrlValue(dayOfWeekUrlValue)
                .orElseThrow(() -> new RadioServiceAdminException("Не існуючий день тижня"))
                .toDayOfWeekResponse();

        model.addAttribute("dayOfWeek", dayOfWeek);

        return "schedule/newItem";
    }

    @GetMapping("/item/delete/{id}")
    public String deleteConfirmation(Model model, @PathVariable("id") String id) {

        ScheduleItemDto scheduleItem = scheduleService.findScheduleItemById(id);

        model.addAttribute("dayOfWeekUrlValue", scheduleItem.getDayOfWeek().getUrlValue());

        return "schedule/deleteConfirmation"; // ToDo merge deleteConfirmations into one shared template
    }

    @PostMapping("/item/new")
    public String create(@ModelAttribute NewScheduleItemForm newScheduleItemForm) { // ToDo add flash message

        ScheduleItemDto scheduleItem = scheduleService.createScheduleItem(newScheduleItemForm);

        return String.format(
                "redirect:/admin/schedule/day/%s#%s", newScheduleItemForm.getDayOfWeekUrlValue(), scheduleItem.getId()
        );
    }

    @PostMapping("/item/update/{id}")
    public String update(@PathVariable("id") String id,
                         @ModelAttribute UpdateScheduleItemForm updateScheduleItemForm) { // ToDo add flash message

        ScheduleItemDto scheduleItem = scheduleService.updateScheduleItem(id, updateScheduleItemForm);

        return String.format("redirect:/admin/schedule/day/%s#%s",
                scheduleItem.getDayOfWeek().getUrlValue(), scheduleItem.getId());
    }

    @PostMapping("/item/delete/{id}")
    public String delete(@PathVariable("id") String id) { // ToDo add flash message

        ScheduleItemDto scheduleItem = scheduleService.findScheduleItemById(id);

        scheduleService.deleteScheduleItem(id);

        return "redirect:/admin/schedule/day/" + scheduleItem.getDayOfWeek().getUrlValue();
    }
}
