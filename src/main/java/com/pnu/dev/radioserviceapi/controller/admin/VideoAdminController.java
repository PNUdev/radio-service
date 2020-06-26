package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.exception.ServiceException;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/videos")
public class VideoAdminController {

    private final VideoService videoService;

    @Autowired
    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public String listVideos(Model model) {
        List<Video> videos = videoService.getAll();
        model.addAttribute("videos", videos);
        return "videos/index";
    }

    @GetMapping("/add")
    public String findVideo(@RequestParam(name = "link", required = false) String link, Model model) {
        if (Objects.isNull(link)) {
            return "videos/addVideo";
        }
        try {
            link = java.net.URLDecoder.decode(link, StandardCharsets.UTF_8.name());
            model.addAttribute("link", link);
            Video video = videoService.findVideoOnYoutubeByLink(link);
            model.addAttribute("video", video);
        } catch (ServiceException | UnsupportedEncodingException e) {
            model.addAttribute("exception", "Відео за посиланням не знайдено");
        }
        return "videos/addVideo";
    }

    @PostMapping("/add")
    public String addVideo(@ModelAttribute Video video) {
        videoService.create(video);
        return "redirect:/admin/videos";
    }

    @PostMapping("/changePriority")
    public String changePriority(@ModelAttribute Video video) {
        videoService.update(video);
        return "redirect:/admin/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        videoService.deleteById(id);
        return "redirect:/admin/videos";
    }

}
