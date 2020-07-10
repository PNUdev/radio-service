package com.pnu.dev.radioserviceapi.controller.admin;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorAdminController implements ErrorController {

    @RequestMapping("/error")
    public String showErrorPage(Model model) {

        model.addAttribute("noHeader", true);
        model.addAttribute("message", "Сторінку не знайдено");
        return "error/show";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
