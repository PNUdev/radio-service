package com.pnu.dev.radioserviceapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static java.util.Objects.nonNull;

@Controller
public class UserController {

    private static final String FLASH_MESSAGE = "flashMessage";

    private static final String FLASH_ERROR = "flashErrorMessage";


    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {

        if (nonNull(error))
            model.addAttribute(FLASH_ERROR, "Невірне ім'я користувача або пароль");

        if (nonNull(logout))
            model.addAttribute(FLASH_MESSAGE, "Ви успішно вийшли");

        return "users/login";
    }
}
