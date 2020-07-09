package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.mongo.RecommendedVideo;
import com.pnu.dev.radioserviceapi.service.RecommendedVideoService;
import com.pnu.dev.radioserviceapi.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/videos")
public class VideoAdminController {

    private static final String FLASH_MESSAGE = "flashMessage";

    private final RecommendedVideoService recommendedVideoService;

    @Autowired
    public VideoAdminController(RecommendedVideoService recommendedVideoService) {
        this.recommendedVideoService = recommendedVideoService;
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "q", required = false) String query,
                          @PageableDefault(size = 5, sort = "priority", direction = Sort.Direction.ASC)
                                  Pageable pageable) {

        Page<RecommendedVideo> programsPage;

        if (StringUtils.isNoneBlank(query)) {
            programsPage = recommendedVideoService.findAllByTitleContains(query, pageable);
            model.addAttribute("searchKeyword", query);
        } else {
            programsPage = recommendedVideoService.findAll(pageable);
        }
        model.addAttribute("maxPriority", recommendedVideoService.getVideosNumber());
        model.addAttribute("videosPage", programsPage);
        return "videos/index";
    }

    @GetMapping("/add")
    public String findVideoOnYoutube() {
        return "videos/addVideo";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfirmation(Model model, @PathVariable("id") String id, HttpServletRequest request) {

        recommendedVideoService.findById(id);

        model.addAttribute("message", "Ви впевнені, що справді хочете видалити рекомендоване відео зі списку?");

        model.addAttribute("returnBackUrl", HttpUtils.getPreviousPageUrl(request));

        return "common/deleteConfirmation";
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
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Порядок відображення відеозаписів було успішно оновлено");
        return "redirect:/admin/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        recommendedVideoService.deleteById(id);
        redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Відео було успішно видалено");
        return "redirect:/admin/videos";
    }

}
