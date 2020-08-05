package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.BannerForm;
import com.pnu.dev.radioserviceapi.mongo.Banner;

public interface BannerService {

    Banner findByBannerTypeValue(String bannerTypeValue);

    void update(String bannerTypeValue, BannerForm bannerForm);

}
