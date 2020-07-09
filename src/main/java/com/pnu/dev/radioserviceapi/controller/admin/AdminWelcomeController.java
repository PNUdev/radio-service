package com.pnu.dev.radioserviceapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminWelcomeController {

    @RequestMapping("/admin")
    public String getWelcomePage() {
        return "adminWelcomePage";
    }
}
