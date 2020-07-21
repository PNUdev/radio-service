package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.mongo.BackgroundImageHrefs;
import com.pnu.dev.radioserviceapi.service.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/backgrounds", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackgroundApiController {

    private BackgroundService backgroundService;

    @Autowired
    public BackgroundApiController(BackgroundService backgroundService) {
        this.backgroundService = backgroundService;
    }

    @GetMapping
    public BackgroundImageHrefs getBackgroundImages() {
        return backgroundService.getBackgroundImageHrefs();
    }

}
