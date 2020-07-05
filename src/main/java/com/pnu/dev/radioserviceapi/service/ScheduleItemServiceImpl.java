package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
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

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private static final Sort SORT_BY_START_TIME = Sort.by(Sort.Direction.ASC, "startTime");

    private ScheduleItemRepository scheduleItemRepository;

    private ProgramRepository programRepository;

    private DataValidator dataValidator;

    private TimeRangeChecker timeRangeChecker;

    @Autowired
    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository,
                                   ProgramRepository programRepository,
                                   DataValidator dataValidator,
                                   TimeRangeChecker timeRangeChecker) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.programRepository = programRepository;
        this.dataValidator = dataValidator;
        this.timeRangeChecker = timeRangeChecker;
    }

    @Override
    public ScheduleItemDto findById(String id) {

        ScheduleItem scheduleItem = scheduleItemRepository.findById(id)
                .orElseThrow(() -> new RadioServiceAdminException("Запис не знайдено у розкладі"));

        return toScheduleItemDto(scheduleItem);
    }

    @Override
    public List<ScheduleItemDto> findAll() {
        return toScheduleItemDto(
                scheduleItemRepository.findAll(SORT_BY_START_TIME)
        );
    }

    @Override
    public List<ScheduleItemDto> findByDayOfWeek(DayOfWeek dayOfWeek) {

        return toScheduleItemDto(
                scheduleItemRepository.findAllByDayOfWeek(dayOfWeek, SORT_BY_START_TIME)
        );
    }

    @Override
    public List<ScheduleItemDto> findByProgramId(String programId) {
        return toScheduleItemDto(
                scheduleItemRepository.findAllByProgramId(programId, SORT_BY_START_TIME)
        );
    }

    @Override
    public OperationResult<ScheduleItemDto> create(NewScheduleItemForm newScheduleItemForm) {

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
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .comment(newScheduleItemForm.getComment())
                .build();

        scheduleItemRepository.save(scheduleItem);

        return OperationResult.success(toScheduleItemDto(scheduleItem));
    }

    @Override
    public OperationResult<ScheduleItemDto> update(String id,
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
                .startTime(startTime)
                .endTime(endTime)
                .comment(updateScheduleItemForm.getComment())
                .build();

        scheduleItemRepository.save(updatedScheduleItem);

        return OperationResult.success(toScheduleItemDto(updatedScheduleItem));
    }

    @Override
    public void deleteById(String id) {
        scheduleItemRepository.deleteById(id);
    }

    private List<ScheduleItemDto> toScheduleItemDto(List<ScheduleItem> scheduleItems) {
        return scheduleItems.stream()
                .map(this::toScheduleItemDto)
                .collect(Collectors.toList());
    }

    private ScheduleItemDto toScheduleItemDto(ScheduleItem scheduleItem) {

        // little bit error prone place, because we expect, that program with this id will be present in db
        Program program = programRepository.findById(scheduleItem.getProgramId()).get();

        return ScheduleItemDto.builder()
                .id(scheduleItem.getId())
                .programName(program.getTitle())
                .programLink("/api/v1/programs/" + program.getId())
                .comment(scheduleItem.getComment())
                .time(com.pnu.dev.radioserviceapi.dto.response.TimeRange.builder()
                        .startTime(scheduleItem.getStartTime())
                        .endTime(scheduleItem.getEndTime())
                        .build())
                .dayOfWeek(scheduleItem.getDayOfWeek().toDayOfWeekResponse())
                .build();
    }
}
