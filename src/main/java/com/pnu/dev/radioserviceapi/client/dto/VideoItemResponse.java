package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoItemResponse {

    private VideoSnippetResponse snippet;

    private VideoIdResponse id;
}

