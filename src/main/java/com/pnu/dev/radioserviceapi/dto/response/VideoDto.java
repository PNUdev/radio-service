package com.pnu.dev.radioserviceapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pnu.dev.radioserviceapi.mongo.LiveBroadcastContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private String id;

    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime publishedAt;

    private LiveBroadcastContent liveBroadcastContent;
}
