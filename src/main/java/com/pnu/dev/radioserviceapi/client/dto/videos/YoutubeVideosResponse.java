package com.pnu.dev.radioserviceapi.client.dto.videos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeVideosResponse {

    private List<ItemYoutubeVideosResponse> items;
}
