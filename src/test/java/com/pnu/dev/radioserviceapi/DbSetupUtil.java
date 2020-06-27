package com.pnu.dev.radioserviceapi;

import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import com.pnu.dev.radioserviceapi.mongo.TimeRange;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Class can be used to setup test db on local machine to start development
@SpringBootTest
class DbSetupUtil {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Test
    @Order(1)
    void programsDbSetup() {

        // setup programs
        List<Program> programs = IntStream.range(0, 20)
                .mapToObj(idx -> Program.builder()
                        .imageUrl("https://www.somagnews.com/wp-content/uploads/2020/04/75-e1586981465263.png")
                        .title("Title " + idx)
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                                "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint" +
                                " occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit" +
                                " anim id est laborum.")
                        .build())
                .collect(Collectors.toList());

        programRepository.saveAll(programs);

    }

    @Test
    @Order(2)
    void scheduleDbSetup() {

        List<Program> programs = programRepository.findAll();

        // setup schedule
        List<ScheduleItem> scheduleItems = Stream.of(
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.SUNDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.MONDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.TUESDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.WEDNESDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.THURSDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.FRIDAY),
                buildScheduleItemsStream(programs.get(0).getId(), DayOfWeek.SATURDAY)
        )
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());

        scheduleItemRepository.saveAll(scheduleItems);

    }

    private Stream<ScheduleItem> buildScheduleItemsStream(String programId, DayOfWeek dayOfWeek) {
        return IntStream.range(0, 6)
                .mapToObj(idx -> ScheduleItem.builder()
                        .programId(programId)
                        .time(TimeRange.builder()
                                .startTime(LocalTime.of(10, 33))
                                .endTime(LocalTime.MAX)
                                .build())
                        .dayOfWeek(dayOfWeek)
                        .comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                        .build());
    }

}
