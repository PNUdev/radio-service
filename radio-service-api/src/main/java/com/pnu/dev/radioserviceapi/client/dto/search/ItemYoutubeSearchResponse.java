package com.pnu.dev.radioserviceapi.client.dto.search;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemYoutubeSearchResponse {

    private VideoIdYoutubeResponse id;

    private SnippetYoutubeResponse snippet;

}

