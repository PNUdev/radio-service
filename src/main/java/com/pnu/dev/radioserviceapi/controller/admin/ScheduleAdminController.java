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
import com.pnu.dev.radioserviceapi.util.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleAdminController {

    private static final String FLASH_MESSAGE = "message";
    public static final String FLASH_ERROR = "error";

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

    @GetMapping("day/{dayOfWeek}")
    public String showForDay(@PathVariable("dayOfWeek") String dayOfWeekValue,
                             @RequestParam(value = "selectedItemId", required = false) String selectedItemId,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        DailySchedule dailySchedule = scheduleService.findForDay(dayOfWeekValue);
        model.addAttribute("dailySchedule", dailySchedule);

        if (nonNull(selectedItemId)) {

            if (model.containsAttribute(FLASH_MESSAGE)) {
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, model.getAttribute(FLASH_MESSAGE));
            }

            if (model.containsAttribute(FLASH_ERROR)) {
                redirectAttributes.addFlashAttribute(FLASH_ERROR, model.getAttribute(FLASH_ERROR));
            }

            redirectAttributes.addFlashAttribute("selectedItemId", selectedItemId);
            return "redirect:/admin/schedule/day/" + dayOfWeekValue;
        }

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
    public String create(@ModelAttribute NewScheduleItemForm newScheduleItemForm,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest httpServletRequest) {

        OperationResult<ScheduleItemDto> scheduleItemOperationResult = scheduleService
                .createScheduleItem(newScheduleItemForm);

        if (scheduleItemOperationResult.isError()) {
            redirectAttributes.addFlashAttribute(FLASH_ERROR, scheduleItemOperationResult.getErrorMessage());
            return redirectToPreviousPage(httpServletRequest);
        }

        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Запис було успішно створено");

        ScheduleItemDto scheduleItem = scheduleItemOperationResult.getData();
        return String.format("redirect:/admin/schedule/day/%s?selectedItemId=%s",
                newScheduleItemForm.getDayOfWeekUrlValue(), scheduleItem.getId());
    }

    @PostMapping("/item/update/{id}")
    public String update(@PathVariable("id") String id,
                         @ModelAttribute UpdateScheduleItemForm updateScheduleItemForm,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest httpServletRequest) {

        OperationResult<ScheduleItemDto> scheduleItemOperationResult = scheduleService
                .updateScheduleItem(id, updateScheduleItemForm);

        if (scheduleItemOperationResult.isError()) {
            redirectAttributes.addFlashAttribute(FLASH_ERROR, scheduleItemOperationResult.getErrorMessage());
            return redirectToPreviousPage(httpServletRequest, id);
        }

        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Запис було успішно оновлено");

        ScheduleItemDto scheduleItem = scheduleItemOperationResult.getData();
        return String.format("redirect:/admin/schedule/day/%s?selectedItemId=%s",
                scheduleItem.getDayOfWeek().getUrlValue(), scheduleItem.getId());
    }

    @PostMapping("/item/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {

        ScheduleItemDto scheduleItem = scheduleService.findScheduleItemById(id);

        scheduleService.deleteScheduleItem(id);

        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Запис було успішно видалено");

        return "redirect:/admin/schedule/day/" + scheduleItem.getDayOfWeek().getUrlValue();
    }

    private String redirectToPreviousPage(HttpServletRequest request, String scheduleItemId) {

        return redirectToPreviousPage(request,
                requestUrl -> String.format("redirect:%s?selectedItemId=%s", requestUrl, scheduleItemId));
    }

    private String redirectToPreviousPage(HttpServletRequest request) {

        return redirectToPreviousPage(request, requestUrl -> "redirect:" + requestUrl);
    }

    private String redirectToPreviousPage(HttpServletRequest request, Function<String, String> buildRedirectUrl) {
        return Optional.ofNullable(request.getHeader("Referer"))
                .map(buildRedirectUrl)
                .orElse("redirect:/admin/schedule");
    }
}
