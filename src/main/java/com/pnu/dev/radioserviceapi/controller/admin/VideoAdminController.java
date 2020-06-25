package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/videos")
public class VideoAdminController {

    // 1. add/remove recommended video

    private VideoService videoService;

    @Autowired
    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public String listVideos() {
        return "videos/index";
    }

    @PostMapping("findVideo")
    public String findVideo(@RequestParam(name = "id") String id) {
        videoService.findVideoOnYoutubeById(id);
        return "videos/index";
    }
}
