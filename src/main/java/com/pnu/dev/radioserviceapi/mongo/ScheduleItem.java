package com.pnu.dev.radioserviceapi.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItem {

    @Id
    private String id;

    private String programId;

    private TimeRange time;

    private DayOfWeek dayOfWeek;

    private String comment;

}
