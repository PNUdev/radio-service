package com.pnu.dev.radioserviceapi.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionInterceptor {

    @ExceptionHandler(Exception.class)
    public ErrorResponse generalError(Exception e) { // ToDo remove this parameter
        return ErrorResponse.builder()
                .message("Internal service error")
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
