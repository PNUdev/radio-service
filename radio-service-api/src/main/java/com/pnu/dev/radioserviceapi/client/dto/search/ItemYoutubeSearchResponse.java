package com.pnu.dev.radioserviceapi.client.dto.search;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class ItemYoutubeSearchResponse {

    private VideoIdYoutubeResponse id;

    private SnippetYoutubeResponse snippet;

}

