package com.pnu.dev.radioserviceapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String getAdminPage() {
        return "adminPage";
    }
}
