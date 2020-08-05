package com.pnu.dev.radioserviceapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.pnu.dev.radioserviceapi.util.FlashMessageConstants.FLASH_MESSAGE_ERROR;
import static com.pnu.dev.radioserviceapi.util.FlashMessageConstants.FLASH_MESSAGE_SUCCESS;
import static java.util.Objects.nonNull;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {

        if (nonNull(error))
            model.addAttribute(FLASH_MESSAGE_ERROR, "Невірне ім'я користувача або пароль");

        if (nonNull(logout))
            model.addAttribute(FLASH_MESSAGE_SUCCESS, "Ви успішно вийшли");

        return "users/login";
    }
}
