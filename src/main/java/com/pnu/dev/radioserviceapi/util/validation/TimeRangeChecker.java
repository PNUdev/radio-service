package com.pnu.dev.radioserviceapi.util.validation;

import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Component
public class TimeRangeChecker {

    private ScheduleItemRepository scheduleItemRepository;

    @Autowired
    public TimeRangeChecker(ScheduleItemRepository scheduleItemRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
    }

    public ValidationResult checkValidAndFreeForCreate(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {

        Predicate<List<ScheduleItem>> isTimeOccupied = scheduleItemsForDay -> scheduleItemsForDay.stream()
                .anyMatch(scheduleItem ->
                        isPeriodsOverlap(scheduleItem.getStartTime(), scheduleItem.getEndTime(), startTime, endTime));

        return checkValidAndFree(startTime, endTime, dayOfWeek, isTimeOccupied);
    }

    public ValidationResult checkValidAndFreeForUpdate(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek,
                                                       String scheduleItemId) {

        Predicate<List<ScheduleItem>> isTimeOccupied = scheduleItemsForDay -> scheduleItemsForDay.stream()
                .filter(scheduleItem -> !StringUtils.equals(scheduleItem.getId(), scheduleItemId))
                .anyMatch(scheduleItem ->
                        isPeriodsOverlap(scheduleItem.getStartTime(), scheduleItem.getEndTime(), startTime, endTime));

        return checkValidAndFree(startTime, endTime, dayOfWeek, isTimeOccupied);
    }

    private ValidationResult checkValidAndFree(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek,
                                               Predicate<List<ScheduleItem>> isTimeOccupied) {

        if (isNull(startTime) || isNull(endTime)) {
            return ValidationResult.withError("Час вказано неправильно");
        }

        if (!startTime.isBefore(endTime)) {
            return ValidationResult.withError("Час початку повинен передувати часу закінчення");
        }

        List<ScheduleItem> scheduleItemsForDay = scheduleItemRepository.findAllByDayOfWeek(dayOfWeek, Sort.unsorted());

        if (isTimeOccupied.test(scheduleItemsForDay)) {
            return ValidationResult.withError("Час зайнятий іншою програмою");
        }

        return ValidationResult.valid();
    }

    private boolean isPeriodsOverlap(LocalTime itemFromDbStartTime, LocalTime itemFromDbEndTime,
                                     LocalTime startTime, LocalTime endTime) {
        return isBetween(itemFromDbStartTime, itemFromDbEndTime, startTime)
                || isBetween(itemFromDbStartTime, itemFromDbEndTime, endTime);
    }

    private boolean isBetween(LocalTime startPeriodTime, LocalTime endPeriodTime, LocalTime timeMoment) {
        return timeMoment.isAfter(startPeriodTime) && timeMoment.isBefore(endPeriodTime);
    }

}
