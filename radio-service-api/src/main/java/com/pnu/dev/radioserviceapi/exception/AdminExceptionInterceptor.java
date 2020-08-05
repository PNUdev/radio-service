package com.pnu.dev.radioserviceapi.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionInterceptor {

    @ExceptionHandler(RadioServiceAdminException.class)
    public String serviceAdminException(RadioServiceAdminException serviceException, Model model) {
        model.addAttribute("message", serviceException.getMessage());
        return "error/show";
    }

}
