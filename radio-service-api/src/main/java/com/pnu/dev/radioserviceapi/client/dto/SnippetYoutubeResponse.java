package com.pnu.dev.radioserviceapi.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Data
public class SnippetYoutubeResponse {

    private LocalDateTime publishedAt;

    private String title;

    private String description;

    private String liveBroadcastContent;

}
