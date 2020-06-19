package com.pnu.dev.radioserviceapi.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Schedule {

    @Id
    private String id;

}
