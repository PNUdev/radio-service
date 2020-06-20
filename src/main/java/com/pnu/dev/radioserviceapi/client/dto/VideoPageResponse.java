package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPageResponse {

    private List<VideoItemResponse> items;

}
