package com.pnu.dev.radioserviceapi.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

    private LocalTime startTime;

    private LocalTime endTime;

}
