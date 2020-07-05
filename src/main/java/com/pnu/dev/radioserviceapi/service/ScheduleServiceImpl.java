package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleItemService scheduleItemService;

    @Autowired
    public ScheduleServiceImpl(ScheduleItemService scheduleItemService) {
        this.scheduleItemService = scheduleItemService;
    }

    @Override
    public Optional<DailySchedule> findForDay(String dayOfWeekValue) {

        return DayOfWeek.findByUrlValue(dayOfWeekValue).map(this::toDailySchedule);
    }

    @Override
    public DailySchedule findForToday() {

        String dayOfWeekValue = LocalDate.now(ZoneId.of("Europe/Kiev"))
                .getDayOfWeek()
                .toString()
                .toLowerCase();

        return findForDay(dayOfWeekValue).get();
    }

    @Override
    public WeeklySchedule findForWeek() {

        List<ScheduleItemDto> scheduleItems = scheduleItemService.findAll();

        return WeeklySchedule.builder()
                .sunday(toDailyScheduleFilter(scheduleItems, DayOfWeek.SUNDAY))
                .monday(toDailyScheduleFilter(scheduleItems, DayOfWeek.MONDAY))
                .tuesday(toDailyScheduleFilter(scheduleItems, DayOfWeek.TUESDAY))
                .wednesday(toDailyScheduleFilter(scheduleItems, DayOfWeek.WEDNESDAY))
                .thursday(toDailyScheduleFilter(scheduleItems, DayOfWeek.THURSDAY))
                .friday(toDailyScheduleFilter(scheduleItems, DayOfWeek.FRIDAY))
                .saturday(toDailyScheduleFilter(scheduleItems, DayOfWeek.SATURDAY))
                .build();
    }

    private DailySchedule toDailySchedule(DayOfWeek dayOfWeek) {
        return toDailySchedule(scheduleItemService.findByDayOfWeek(dayOfWeek), dayOfWeek);
    }

    private DailySchedule toDailyScheduleFilter(List<ScheduleItemDto> scheduleItems, DayOfWeek dayOfWeek) {

        List<ScheduleItemDto> scheduleItemsForDay = scheduleItems.stream()
                .filter(scheduleItemDto ->
                        StringUtils.equals(scheduleItemDto.getDayOfWeek().getUrlValue(), dayOfWeek.getUrlValue()))
                .collect(Collectors.toList());

        return toDailySchedule(scheduleItemsForDay, dayOfWeek);
    }

    private DailySchedule toDailySchedule(List<ScheduleItemDto> scheduleItems, DayOfWeek dayOfWeek) {

        return DailySchedule.builder()
                .dayOfWeek(dayOfWeek.toDayOfWeekResponse())
                .scheduleItems(scheduleItems)
                .build();

    }

}
