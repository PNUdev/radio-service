package com.pnu.dev.radioserviceapi.client.dto.videos;

import lombok.Data;

import java.util.List;


@Data
public class YoutubeVideosResponse {

    private List<ItemYoutubeVideosResponse> items;
}
