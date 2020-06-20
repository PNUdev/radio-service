package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoSnippetResponse {
    private LocalDateTime publishedAt;
    private String title;
    private String description;

}
