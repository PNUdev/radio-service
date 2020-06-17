package com.pnu.dev.radioserviceapi.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoApiController {

    // DTO with :
    // 1. recent - list of recent videos
    // 2. recommended - list of recommended videos (call out to youtube api)
    // 3. planned or current streams

}
