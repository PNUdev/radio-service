package com.pnu.dev.radioserviceapi.client.dto.videos;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ItemYoutubeVideosResponse {

    private String id;

    private SnippetYoutubeResponse snippet;

}
