package com.pnu.dev.radioserviceapi.dto.form;

import lombok.Data;

// ToDo add validations
@Data
public class UpdateScheduleItemForm {

    private String comment;

    private String startTime;

    private String endTime;

}
