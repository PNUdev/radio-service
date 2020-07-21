package com.pnu.dev.radioserviceapi.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundImageHrefs {

    public static final String SINGLETON_RECORD_ID = "objectId";

    @Id
    @JsonIgnore
    private String id = SINGLETON_RECORD_ID;

    private String radioPage;

    private String schedulePage;

    private String programsPage;

    private String recommendedVideosPage;

    private String recentVideosPage;


}
