package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageYoutubeResponse {

    private String url;

    private int width;

    private int height;
}
