package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.mongo.BackgroundImageHrefs;
import com.pnu.dev.radioserviceapi.service.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.pnu.dev.radioserviceapi.util.FlashMessageConstants.FLASH_MESSAGE_SUCCESS;

@Controller
@RequestMapping("/admin/backgrounds")
public class BackgroundAdminController {

    private BackgroundService backgroundService;

    @Autowired
    public BackgroundAdminController(BackgroundService backgroundService) {
        this.backgroundService = backgroundService;
    }

    @GetMapping
    public String showIndex(Model model) {

        BackgroundImageHrefs backgroundImageHrefs = backgroundService.getBackgroundImageHrefs();
        model.addAttribute("imageHrefs", backgroundImageHrefs);

        return "backgrounds/index";
    }


    @PostMapping
    public String update(@ModelAttribute BackgroundImageHrefs backgroundImageHrefs,
                         RedirectAttributes redirectAttributes) {

        backgroundService.update(backgroundImageHrefs);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE_SUCCESS, "Зміни успішно збережно");

        return "redirect:/admin/backgrounds";
    }

}
