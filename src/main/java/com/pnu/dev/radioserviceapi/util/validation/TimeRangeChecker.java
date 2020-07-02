package com.pnu.dev.radioserviceapi.util.validation;

import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
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

    public void checkValidAndFreeForCreate(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {

        Predicate<List<ScheduleItem>> isTimeOccupied = scheduleItemsForDay -> scheduleItemsForDay.stream()
                .map(ScheduleItem::getTime)
                .anyMatch(timeRange -> isPeriodsOverlap(timeRange, startTime, endTime));

        checkValidAndFree(startTime, endTime, dayOfWeek, isTimeOccupied);

    }

    public void checkValidAndFreeForUpdate(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek,
                                           String scheduleItemId) {

        Predicate<List<ScheduleItem>> isTimeOccupied = scheduleItemsForDay -> scheduleItemsForDay.stream()
                .filter(scheduleItem -> !StringUtils.equals(scheduleItem.getId(), scheduleItemId))
                .map(ScheduleItem::getTime)
                .anyMatch(timeRange -> isPeriodsOverlap(timeRange, startTime, endTime));

        checkValidAndFree(startTime, endTime, dayOfWeek, isTimeOccupied);

    }

    private void checkValidAndFree(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek,
                                   Predicate<List<ScheduleItem>> isTimeOccupied) {

        if (isNull(startTime) || isNull(endTime)) {
            throw new RadioServiceAdminException("Час вказано неправильно");
        }

        if (!startTime.isBefore(endTime)) {
            throw new RadioServiceAdminException("Час початку повинен передувати часу закінчення");
        }

        List<ScheduleItem> scheduleItemsForDay = scheduleItemRepository.findByDayOfWeek(dayOfWeek, Sort.unsorted());

        if (isTimeOccupied.test(scheduleItemsForDay)) {
            throw new RadioServiceAdminException("Час зайнятий іншою програмою");
        }

    }

    private boolean isPeriodsOverlap(TimeRange timeRange, LocalTime startTime, LocalTime endTime) {
        return isBetween(timeRange, startTime) || isBetween(timeRange, endTime);
    }

    private boolean isBetween(TimeRange timeRange, LocalTime timeMoment) {
        return timeMoment.isAfter(timeRange.getStartTime()) && timeMoment.isBefore(timeRange.getEndTime());
    }

}
