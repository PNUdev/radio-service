package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import com.pnu.dev.radioserviceapi.service.RecommendedVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/videos")
public class VideoAdminController {

    private static final String FLASH_MESSAGE = "message";

    private final RecommendedVideoService recommendedVideoService;

    @Autowired
    public VideoAdminController(RecommendedVideoService recommendedVideoService) {
        this.recommendedVideoService = recommendedVideoService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<RecommendedVideo> recommendedVideos = recommendedVideoService.findAll();
        model.addAttribute("videos", recommendedVideos);
        return "videos/index";
    }

    @GetMapping("/add")
    public String findVideoOnYoutube() {
        return "videos/addVideo";
    }

    @PostMapping("/add")
    public String addVideo(@ModelAttribute(name = "link") String link, RedirectAttributes redirectAttributes) {
        recommendedVideoService.create(link);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Відео було успішно додано");
        return "redirect:/admin/videos";
    }

    @PostMapping("/updatePriority/{id}")
    public String update(@PathVariable("id") String id, @ModelAttribute(name = "newPriority") Integer newPriority,
                         RedirectAttributes redirectAttributes) {
        recommendedVideoService.updatePriority(id, newPriority);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Відео було успішно оновлено");
        return "redirect:/admin/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        recommendedVideoService.deleteById(id);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Відео було успішно видалено");
        return "redirect:/admin/videos";
    }

}
