package com.pnu.dev.radioserviceapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {

    private String id;

    private String title;

    private String description;

    private String imageUrl;

    private List<ScheduleOccurrence> scheduleOccurrences;

}
