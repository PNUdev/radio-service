package com.pnu.dev.radioserviceapi.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateScheduleItemForm {

    private String comment;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String endTime;

}
