package com.pnu.dev.radioserviceapi.client.dto.videos;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.Data;


@Data
public class ItemYoutubeVideosResponse {

    private String id;

    private SnippetYoutubeResponse snippet;

}
