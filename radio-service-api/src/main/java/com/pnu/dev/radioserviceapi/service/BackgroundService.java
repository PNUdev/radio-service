package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.BackgroundImageHrefs;

public interface BackgroundService {

    BackgroundImageHrefs getBackgroundImageHrefs();

    void update(BackgroundImageHrefs backgroundImageHrefs);

}
