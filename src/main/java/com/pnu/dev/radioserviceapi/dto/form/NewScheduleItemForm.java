package com.pnu.dev.radioserviceapi.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewScheduleItemForm {

    @NotEmpty
    private String dayOfWeekUrlValue;

    @NotEmpty
    private String programId;

    private String comment;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String endTime;

}
