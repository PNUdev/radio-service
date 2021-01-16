package com.pnu.dev.radioserviceapi.client.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeSearchResponse {

    private List<ItemYoutubeSearchResponse> items;

}
