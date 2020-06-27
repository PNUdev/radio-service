package com.pnu.dev.radioserviceapi.dto.schedule;

import com.pnu.dev.radioserviceapi.mongo.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItemDto {

    private String id;

    private String programName;

    private String programLink;

    private TimeRange time;

    private String comment;

}
