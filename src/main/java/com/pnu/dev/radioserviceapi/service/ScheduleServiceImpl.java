package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.mongo.TimeRange;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import com.pnu.dev.radioserviceapi.util.validation.DataValidator;
import com.pnu.dev.radioserviceapi.util.validation.TimeRangeChecker;
import com.pnu.dev.radioserviceapi.util.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

// ToDo move all the mapping logic to separate class
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Sort SORT_BY_START_TIME = Sort.by("time.startTime"); // ToDo Sort do not work the way it supposed to, fix it

    private ScheduleItemRepository scheduleItemRepository;

    private ProgramRepository programRepository;

    private DataValidator dataValidator;

    private TimeRangeChecker timeRangeChecker;

    @Autowired
    public ScheduleServiceImpl(ScheduleItemRepository scheduleItemRepository,
                               ProgramRepository programRepository,
                               DataValidator dataValidator,
                               TimeRangeChecker timeRangeChecker) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.programRepository = programRepository;
        this.dataValidator = dataValidator;
        this.timeRangeChecker = timeRangeChecker;
    }

    @Override
    public DailySchedule findForDay(String dayOfWeekValue) {

        DayOfWeek dayOfWeek = DayOfWeek.findByUrlValue(dayOfWeekValue)
                .orElseThrow(() -> new RadioServiceAdminException("Сторінки не існує"));

        List<ScheduleItem> scheduleItems = scheduleItemRepository.findByDayOfWeek(dayOfWeek, SORT_BY_START_TIME);

        return toDailySchedule(scheduleItems, dayOfWeek);
    }

    @Override
    public List<ScheduleItemDto> findForProgram(String programId) {
        return scheduleItemRepository.findAllByProgramId(programId).stream()
                .map(this::toScheduleItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public WeeklySchedule findForWeek() { // ToDo this method should be used only from api

        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAll(SORT_BY_START_TIME);

        return WeeklySchedule.builder()
                .sunday(toDailySchedule(scheduleItems, DayOfWeek.SUNDAY))
                .monday(toDailySchedule(scheduleItems, DayOfWeek.MONDAY))
                .tuesday(toDailySchedule(scheduleItems, DayOfWeek.TUESDAY))
                .wednesday(toDailySchedule(scheduleItems, DayOfWeek.WEDNESDAY))
                .thursday(toDailySchedule(scheduleItems, DayOfWeek.THURSDAY))
                .friday(toDailySchedule(scheduleItems, DayOfWeek.FRIDAY))
                .saturday(toDailySchedule(scheduleItems, DayOfWeek.SATURDAY))
                .build();
    }

    @Override
    public ScheduleItemDto findScheduleItemById(String id) { // ToDo remove this method if not used

        ScheduleItem scheduleItem = scheduleItemRepository.findById(id)
                .orElseThrow(() -> new RadioServiceAdminException("Exception")); // ToDo

        return toScheduleItemDto(scheduleItem);
    }

    @Override
    public OperationResult<ScheduleItemDto> createScheduleItem(NewScheduleItemForm newScheduleItemForm) {

        ValidationResult formValidation = dataValidator.validate(newScheduleItemForm);

        if (formValidation.isError()) {
            throw new RadioServiceAdminException("Обов'язкові поля не заповнені");
        }

        DayOfWeek dayOfWeek = DayOfWeek.findByUrlValue(newScheduleItemForm.getDayOfWeekUrlValue())
                .orElseThrow(() -> new RadioServiceAdminException("Неіснуючий день тижня"));

        LocalTime startTime = LocalTime.parse(newScheduleItemForm.getStartTime());
        LocalTime endTime = LocalTime.parse(newScheduleItemForm.getEndTime());

        ValidationResult validationResult = timeRangeChecker
                .checkValidAndFreeForCreate(startTime, endTime, dayOfWeek);

        if (validationResult.isError()) {
            return OperationResult.error(validationResult.getErrorMessage());
        }

        ScheduleItem scheduleItem = ScheduleItem.builder()
                .programId(newScheduleItemForm.getProgramId())
                .time(TimeRange.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .build())
                .dayOfWeek(dayOfWeek)
                .comment(newScheduleItemForm.getComment())
                .build();

        scheduleItemRepository.save(scheduleItem);

        return OperationResult.success(toScheduleItemDto(scheduleItem));
    }

    @Override
    public OperationResult<ScheduleItemDto> updateScheduleItem(String id,
                                                               UpdateScheduleItemForm updateScheduleItemForm) {

        ValidationResult formValidation = dataValidator.validate(updateScheduleItemForm);

        if (formValidation.isError()) {
            throw new RadioServiceAdminException("Обов'язкові поля не заповнені");
        }

        ScheduleItem scheduleItemFromDb = scheduleItemRepository.findById(id)
                .orElseThrow(() -> new RadioServiceAdminException("Спроба оновити не існуючий запис"));

        LocalTime startTime = LocalTime.parse(updateScheduleItemForm.getStartTime());
        LocalTime endTime = LocalTime.parse(updateScheduleItemForm.getEndTime());

        ValidationResult validationResult = timeRangeChecker
                .checkValidAndFreeForUpdate(startTime, endTime, scheduleItemFromDb.getDayOfWeek(), id);

        if (validationResult.isError()) {
            return OperationResult.error(validationResult.getErrorMessage());
        }

        ScheduleItem updatedScheduleItem = scheduleItemFromDb.toBuilder()
                .time(TimeRange.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .build())
                .comment(updateScheduleItemForm.getComment())
                .build();

        scheduleItemRepository.save(updatedScheduleItem);

        return OperationResult.success(toScheduleItemDto(updatedScheduleItem));
    }

    @Override
    public void deleteScheduleItem(String id) {
        scheduleItemRepository.deleteById(id);
    }

    private DailySchedule toDailySchedule(List<ScheduleItem> scheduleItems, DayOfWeek dayOfWeek) { // ToDo move to separate class

        return DailySchedule.builder()
                .dayOfWeek(dayOfWeek.toDayOfWeekResponse())
                .scheduleItems(
                        scheduleItems.stream()
                                .filter(scheduleItem -> scheduleItem.getDayOfWeek() == dayOfWeek)
                                .map(this::toScheduleItemDto)
                                .collect(Collectors.toList())
                )
                .build();

    }

    private ScheduleItemDto toScheduleItemDto(ScheduleItem scheduleItem) {

        Program program = programRepository.findById(scheduleItem.getProgramId())
                .orElseThrow(() -> new RadioServiceApiException("Exception")); // ToDo move to separate class use OperationResult here

        return ScheduleItemDto.builder()
                .id(scheduleItem.getId())
                .programName(program.getTitle())
                .programLink("/api/v1/programs/" + program.getId())
                .comment(scheduleItem.getComment())
                .time(com.pnu.dev.radioserviceapi.dto.response.TimeRange.builder()
                        .startTime(scheduleItem.getTime().getStartTime())
                        .endTime(scheduleItem.getTime().getEndTime())
                        .build())
                .dayOfWeek(scheduleItem.getDayOfWeek().toDayOfWeekResponse())
                .build();
    }
}
