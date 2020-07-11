package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.service.BannerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/banners", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerApiController {

    private BannerApiService bannerApiService;

    @Autowired
    public BannerApiController(BannerApiService bannerApiService) {
        this.bannerApiService = bannerApiService;
    }

    @GetMapping
    public Map<String, String> findBannersToShow() {
        return bannerApiService.findBannersToShow();
    }

}
