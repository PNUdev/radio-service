package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Banner;
import com.pnu.dev.radioserviceapi.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BannerApiServiceImpl implements BannerApiService {

    private BannerRepository bannerRepository;

    @Autowired
    public BannerApiServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Map<String, String> findBannersToShow() {
        return bannerRepository.findAllByShowTrue().stream()
                .collect(Collectors.toMap(this::getBannerTypeValue, Banner::getMarkdown));
    }

    private String getBannerTypeValue(Banner banner) {
        return banner.getBannerType().getBannerTypeValue();
    }

}
