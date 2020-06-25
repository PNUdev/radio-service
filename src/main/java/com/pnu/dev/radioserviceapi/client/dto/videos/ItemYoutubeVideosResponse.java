package com.pnu.dev.radioserviceapi.client.dto.videos;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemYoutubeVideosResponse {

    private String id;

    private SnippetYoutubeResponse snippet;
}
