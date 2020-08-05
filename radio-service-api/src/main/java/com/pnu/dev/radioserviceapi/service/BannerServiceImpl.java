package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.BannerForm;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.Banner;
import com.pnu.dev.radioserviceapi.mongo.BannerType;
import com.pnu.dev.radioserviceapi.repository.BannerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {

    private BannerRepository bannerRepository;

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Banner findByBannerTypeValue(String bannerTypeValue) {

        BannerType bannerType = BannerType.findByBannerTypeValue(bannerTypeValue)
                .orElseThrow(() -> new RadioServiceAdminException("Невідомий тип банера"));

        return bannerRepository.findById(bannerType)
                .orElseGet(() -> createAndSaveEmptyBanner(bannerType));
    }

    @Override
    public void update(String bannerTypeValue, BannerForm bannerForm) {

        Banner banner = findByBannerTypeValue(bannerTypeValue);

        Banner updatedBanner = banner.toBuilder()
                .markdown(bannerForm.getMarkdown())
                .show(bannerForm.isShow())
                .build();

        bannerRepository.save(updatedBanner);
    }

    private Banner createAndSaveEmptyBanner(BannerType bannerType) {
        Banner banner = Banner.builder()
                .bannerType(bannerType)
                .markdown(StringUtils.EMPTY)
                .build();

        bannerRepository.save(banner);
        return banner;
    }

}
