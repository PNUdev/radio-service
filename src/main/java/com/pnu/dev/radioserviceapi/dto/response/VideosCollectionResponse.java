package com.pnu.dev.radioserviceapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideosCollectionResponse {

    private List<VideoDto> recent;

    private List<VideoDto> streams;
}
