package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.dto.form.BannerForm;
import com.pnu.dev.radioserviceapi.mongo.Banner;
import com.pnu.dev.radioserviceapi.mongo.BannerType;
import com.pnu.dev.radioserviceapi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/banners")
public class BannerAdminController {

    private BannerService bannerService;

    @Autowired
    public BannerAdminController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public String showIndex(Model model) {

        model.addAttribute("bannerTypes", BannerType.values());
        return "banner/index";
    }

    @GetMapping("/edit/{bannerTypeValue}")
    public String edit(@PathVariable("bannerTypeValue") String bannerTypeValue, Model model) {

        Banner banner = bannerService.findByBannerTypeValue(bannerTypeValue);
        model.addAttribute("banner", banner);

        return "banner/edit";
    }

    @PostMapping("/update/{bannerType}")
    public String update(@PathVariable("bannerType") String bannerTypeValue,
                         @ModelAttribute BannerForm bannerForm,
                         RedirectAttributes redirectAttributes) {

        bannerService.update(bannerTypeValue, bannerForm);

        redirectAttributes.addFlashAttribute("flashMessage", "Зміни збережено");
        return "redirect:/admin/banners/edit/" + bannerTypeValue;
    }

}
