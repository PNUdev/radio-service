package com.pnu.dev.radioserviceapi.client.dto.search;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class VideoIdYoutubeResponse {

    private String videoId;

}
