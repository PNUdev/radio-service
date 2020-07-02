package com.pnu.dev.radioserviceapi.dto.form;

import lombok.Data;

@Data
public class NewScheduleItemForm {

    private String dayOfWeekUrlValue;

    private String programId;

    private String comment;

    private String startTime;

    private String endTime;

}
