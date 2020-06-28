package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.schedule.DailySchedule;
import com.pnu.dev.radioserviceapi.dto.schedule.ScheduleItemDto;
import com.pnu.dev.radioserviceapi.dto.schedule.WeeklySchedule;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.exception.RadioServiceApiException;
import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// ToDo move all the mapping logic to separate class
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleItemRepository scheduleItemRepository;

    private ProgramRepository programRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleItemRepository scheduleItemRepository, ProgramRepository programRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
        this.programRepository = programRepository;
    }

    @Override
    public DailySchedule findForDay(String dayOfWeekValue) {

        DayOfWeek dayOfWeek = DayOfWeek.findByValue(dayOfWeekValue)
                .orElseThrow(() -> new RadioServiceAdminException("Сторінки не існує"));


        List<ScheduleItem> scheduleItems = scheduleItemRepository.findByDayOfWeek(dayOfWeek);

        return toDailySchedule(scheduleItems, dayOfWeek);
    }

    @Override
    public WeeklySchedule findForWeek() { // ToDo this method should be used only from api

        List<ScheduleItem> scheduleItems = scheduleItemRepository.findAll();

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

    private DailySchedule toDailySchedule(List<ScheduleItem> scheduleItems, DayOfWeek dayOfWeek) { // ToDo move to separate class

        return DailySchedule.builder()
                .dayOfWeekName(dayOfWeek.getHumanReadableName())
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
                .time(scheduleItem.getTime())
                .build();
    }
}
