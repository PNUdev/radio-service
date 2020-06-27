package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.mongo.Video;
import com.pnu.dev.radioserviceapi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/videos")
public class VideoAdminController {

    private final VideoService videoService;

    @Autowired
    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "videos/index";
    }

    @GetMapping("/add")
    public String findVideoOnYoutube() {
        return "videos/addVideo";
    }

    @PostMapping("/add")
    public String addVideo(@ModelAttribute(name = "link") String link) {
        videoService.create(link);
        return "redirect:/admin/videos";
    }

    @PostMapping("/changePriority/{id}")
    public String update(@PathVariable("id") String id, @ModelAttribute(name = "newPriority") Integer newPriority) {
        videoService.updatePriority(id, newPriority);
        return "redirect:/admin/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        videoService.deleteById(id);
        return "redirect:/admin/videos";
    }

}
