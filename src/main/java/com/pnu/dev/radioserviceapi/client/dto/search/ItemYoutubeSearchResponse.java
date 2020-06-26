package com.pnu.dev.radioserviceapi.client.dto.search;

import com.pnu.dev.radioserviceapi.client.dto.SnippetYoutubeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemYoutubeSearchResponse {

    private VideoIdYoutubeSearchResponse id;

    private SnippetYoutubeResponse snippet;

}

