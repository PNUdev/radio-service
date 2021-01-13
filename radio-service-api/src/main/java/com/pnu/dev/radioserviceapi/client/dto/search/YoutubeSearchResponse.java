package com.pnu.dev.radioserviceapi.client.dto.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class YoutubeSearchResponse {

    private List<ItemYoutubeSearchResponse> items;

}
