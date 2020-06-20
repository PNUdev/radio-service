package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.client.YoutubeApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoApiController {

    // DTO with :
    // 1. recent - list of recent videos (call out to youtube api)
    // 2. recommended - list of recommended videos
    // 3. planned or current streams

}
