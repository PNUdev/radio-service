package com.pnu.dev.radioserviceapi.client.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class YoutubeApiResult<T> {

    T data;

    String errorMessage;

    public boolean isError() {
        return StringUtils.isNotBlank(errorMessage);
    }

    public static <T> YoutubeApiResult<T> success(T data) {
        return YoutubeApiResult.<T>builder()
                .data(data)
                .build();
    }

    public static <T> YoutubeApiResult<T> error(String errorMessage) {
        return YoutubeApiResult.<T>builder()
                .errorMessage(errorMessage)
                .build();
    }

}
