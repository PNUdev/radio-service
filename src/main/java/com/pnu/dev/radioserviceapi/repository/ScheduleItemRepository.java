package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.DayOfWeek;
import com.pnu.dev.radioserviceapi.mongo.ScheduleItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleItemRepository extends MongoRepository<ScheduleItem, String> {

    List<ScheduleItem> findByDayOfWeek(DayOfWeek dayOfWeek);

}
