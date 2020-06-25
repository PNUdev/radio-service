package com.pnu.dev.radioserviceapi.client.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeSearchResponse {

    private List<ItemYoutubeSearchResponse> items;

}
