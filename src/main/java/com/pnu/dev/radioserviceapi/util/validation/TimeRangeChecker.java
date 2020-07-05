package com.pnu.dev.radioserviceapi.util.validation;

import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.mongo.TimeRange;
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
                .map(ScheduleItem::getTime)
                .anyMatch(timeRange -> isPeriodsOverlap(timeRange, startTime, endTime));

        return checkValidAndFree(startTime, endTime, dayOfWeek, isTimeOccupied);
    }

    public ValidationResult checkValidAndFreeForUpdate(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek,
                                                       String scheduleItemId) {

        Predicate<List<ScheduleItem>> isTimeOccupied = scheduleItemsForDay -> scheduleItemsForDay.stream()
                .filter(scheduleItem -> !StringUtils.equals(scheduleItem.getId(), scheduleItemId))
                .map(ScheduleItem::getTime)
                .anyMatch(timeRange -> isPeriodsOverlap(timeRange, startTime, endTime));

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

    private boolean isPeriodsOverlap(TimeRange timeRange, LocalTime startTime, LocalTime endTime) {
        return isBetween(timeRange, startTime) || isBetween(timeRange, endTime);
    }

    private boolean isBetween(TimeRange timeRange, LocalTime timeMoment) {
        return timeMoment.isAfter(timeRange.getStartTime()) && timeMoment.isBefore(timeRange.getEndTime());
    }

}
