package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnippetYoutubeResponse {

    private LocalDateTime publishedAt;

    private String title;

    private String description;

    private String liveBroadcastContent;

}
