package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.response.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.response.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.response.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.exception.InternalServiceError;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.util.mapper.ScheduleItemsToDailyScheduleMapper;
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

    private ScheduleItemsToDailyScheduleMapper scheduleItemsToDailyScheduleMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleItemService scheduleItemService,
                               ScheduleItemsToDailyScheduleMapper scheduleItemsToDailyScheduleMapper) {

        this.scheduleItemService = scheduleItemService;
        this.scheduleItemsToDailyScheduleMapper = scheduleItemsToDailyScheduleMapper;
    }

    @Override
    public Optional<DailySchedule> findForDay(String dayOfWeekValue) {

        return DayOfWeek.findByUrlValue(dayOfWeekValue).map(dayOfWeek -> scheduleItemsToDailyScheduleMapper
                .map(scheduleItemService.findByDayOfWeek(dayOfWeek), dayOfWeek)
        );
    }

    @Override
    public DailySchedule findForToday() {

        String dayOfWeekValue = LocalDate.now(ZoneId.of("Europe/Kiev"))
                .getDayOfWeek()
                .toString()
                .toLowerCase();

        return findForDay(dayOfWeekValue).orElseThrow(() -> new InternalServiceError("Внутрішня помилка сервера"));
    }

    @Override
    public WeeklySchedule findForWeek() {

        List<ScheduleItemDto> scheduleItems = scheduleItemService.findAll();

        return WeeklySchedule.builder()
                .sunday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.SUNDAY))
                .monday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.MONDAY))
                .tuesday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.TUESDAY))
                .wednesday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.WEDNESDAY))
                .thursday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.THURSDAY))
                .friday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.FRIDAY))
                .saturday(filterByDayOfWeekAndMapToDailySchedule(scheduleItems, DayOfWeek.SATURDAY))
                .build();
    }

    private DailySchedule filterByDayOfWeekAndMapToDailySchedule(List<ScheduleItemDto> scheduleItems,
                                                                 DayOfWeek dayOfWeek) {

        List<ScheduleItemDto> scheduleItemsForDay = scheduleItems.stream()
                .filter(scheduleItemDto ->
                        StringUtils.equals(scheduleItemDto.getDayOfWeek().getUrlValue(), dayOfWeek.getUrlValue()))
                .collect(Collectors.toList());

        return scheduleItemsToDailyScheduleMapper.map(scheduleItemsForDay, dayOfWeek);
    }

}
