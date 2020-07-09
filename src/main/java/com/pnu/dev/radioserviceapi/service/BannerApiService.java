package com.pnu.dev.radioserviceapi.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BannerApiService {

    Map<String, String> findBannersToShow();

}
