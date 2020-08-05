package com.pnu.dev.radioserviceapi.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionInterceptor {

    @ExceptionHandler(Exception.class)
    public ErrorResponse generalError() {
        return ErrorResponse.builder()
                .message("Внутрішня помилка сервера")
                .status(500)
                .build();
    }

    @ExceptionHandler(InternalServiceError.class)
    public ErrorResponse internalServiceError(InternalServiceError internalServiceError) {
        return ErrorResponse.builder()
                .message(internalServiceError.getMessage())
                .status(500)
                .build();
    }


    @ExceptionHandler(RadioServiceApiException.class)
    public ErrorResponse serviceApiException(RadioServiceApiException serviceException) {
        return ErrorResponse.builder()
                .message(serviceException.getMessage())
                .status(400)
                .build();
    }

}
