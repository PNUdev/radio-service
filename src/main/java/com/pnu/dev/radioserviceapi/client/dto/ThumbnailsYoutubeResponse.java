package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailsYoutubeResponse {

    private ImageYoutubeResponse medium;

    private ImageYoutubeResponse high;

    private ImageYoutubeResponse standard;

    private ImageYoutubeResponse maxres;

    public ImageYoutubeResponse getHigh() {
        return !Objects.isNull(high) ? high : getStandard();
    }

    public ImageYoutubeResponse getStandard() {
        return !Objects.isNull(standard) ? standard : getHigh();
    }

    public ImageYoutubeResponse getMaxres() {
        return !Objects.isNull(maxres) ? maxres : getStandard();
    }
}

