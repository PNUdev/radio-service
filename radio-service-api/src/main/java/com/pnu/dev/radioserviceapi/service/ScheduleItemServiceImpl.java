package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.NewScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.form.UpdateScheduleItemForm;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import com.pnu.dev.radioserviceapi.util.OperationResult;
import com.pnu.dev.radioserviceapi.util.mapper.ScheduleItemToScheduleItemDtoMapper;
import com.pnu.dev.radioserviceapi.util.validation.DataValidator;
import com.pnu.dev.radioserviceapi.util.validation.TimeRangeChecker;
import com.pnu.dev.radioserviceapi.util.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private ScheduleItemRepository scheduleItemRepository;

    private ScheduleItemToScheduleItemDtoMapper scheduleItemToScheduleItemDtoMapper;

    private DataValidator dataValidator;

    private TimeRangeChecker timeRangeChecker;

    @Autowired
    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository,
                                   ScheduleItemToScheduleItemDtoMapper scheduleItemToScheduleItemDtoMapper,
                                   DataValidator dataValidator,
                                   TimeRangeChecker timeRangeChecker) {

        this.scheduleItemRepository = scheduleItemRepository;
        this.scheduleItemToScheduleItemDtoMapper = scheduleItemToScheduleItemDtoMapper;
        this.dataValidator = dataValidator;
        this.timeRangeChecker = timeRangeChecker;
    }

    @Override
    public Optional<ScheduleItemDto> findById(String id) {

        return scheduleItemRepository.findById(id).map(scheduleItemToScheduleItemDtoMapper::map);
    }

    @Override
    public List<ScheduleItemDto> findAll() {

        return sortByStartTimeAndMapToScheduleItemDtos(
                scheduleItemRepository.findAll()
        );
    }

    @Override
    public List<ScheduleItemDto> findByDayOfWeek(DayOfWeek dayOfWeek) {

        return sortByStartTimeAndMapToScheduleItemDtos(
                scheduleItemRepository.findAllByDayOfWeek(dayOfWeek)
        );
    }

    @Override
    public List<ScheduleItemDto> findByProgramId(String programId) {

        return sortByStartTimeAndMapToScheduleItemDtos(
                scheduleItemRepository.findAllByProgramId(programId)
        );
    }

    @Override
    public OperationResult<ScheduleItemDto> create(NewScheduleItemForm newScheduleItemForm) {

        ValidationResult formValidation = dataValidator.validate(newScheduleItemForm);

        if (formValidation.isError()) {
            return OperationResult.error("Обов'язкові поля не заповнені");
        }

        Optional<DayOfWeek> dayOfWeek = DayOfWeek.findByUrlValue(newScheduleItemForm.getDayOfWeekUrlValue());

        if (!dayOfWeek.isPresent()) {
            return OperationResult.error("Неіснуючий день тижня");
        }

        LocalTime startTime = LocalTime.parse(newScheduleItemForm.getStartTime());
        LocalTime endTime = LocalTime.parse(newScheduleItemForm.getEndTime());

        ValidationResult validationResult = timeRangeChecker
                .checkValidAndFreeForCreate(startTime, endTime, dayOfWeek.get());

        if (validationResult.isError()) {
            return OperationResult.error(validationResult.getErrorMessage());
        }

        ScheduleItem scheduleItem = ScheduleItem.builder()
                .programId(newScheduleItemForm.getProgramId())
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek.get())
                .comment(newScheduleItemForm.getComment())
                .build();

        scheduleItemRepository.save(scheduleItem);

        return OperationResult.success(
                scheduleItemToScheduleItemDtoMapper.map(scheduleItem)
        );
    }

    @Override
    public OperationResult<ScheduleItemDto> update(String id,
                                                   UpdateScheduleItemForm updateScheduleItemForm) {

        ValidationResult formValidation = dataValidator.validate(updateScheduleItemForm);

        if (formValidation.isError()) {
            return OperationResult.error("Обов'язкові поля не заповнені");
        }

        Optional<ScheduleItem> scheduleItemFromDbOptional = scheduleItemRepository.findById(id);

        if (!scheduleItemFromDbOptional.isPresent()) {
            return OperationResult.error("Спроба оновити неіснуючий запис");
        }

        ScheduleItem scheduleItemFromDb = scheduleItemFromDbOptional.get();

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

        return OperationResult.success(
                scheduleItemToScheduleItemDtoMapper.map(updatedScheduleItem)
        );
    }

    @Override
    public void deleteById(String id) {
        scheduleItemRepository.deleteById(id);
    }

    private List<ScheduleItemDto> sortByStartTimeAndMapToScheduleItemDtos(List<ScheduleItem> scheduleItems) {
        List<ScheduleItem> sortedScheduleItems = scheduleItems.stream()
                .sorted(Comparator.comparing(ScheduleItem::getStartTime))
                .collect(Collectors.toList());

        return scheduleItemToScheduleItemDtoMapper.map(sortedScheduleItems);
    }

}
