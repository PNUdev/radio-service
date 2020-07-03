package com.pnu.dev.radioserviceapi.util;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class OperationResult<T> {

    private T data;

    private String errorMessage;

    public boolean isError() {
        return StringUtils.isNotBlank(errorMessage);
    }

    public static <T> OperationResult<T> success(T data) {
        return OperationResult.<T>builder()
                .data(data)
                .build();
    }

    public static <T> OperationResult<T> error(String errorMessage) {
        return OperationResult.<T>builder()
                .errorMessage(errorMessage)
                .build();
    }

}
