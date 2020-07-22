package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.BackgroundImageHrefs;
import com.pnu.dev.radioserviceapi.repository.BackgroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackgroundServiceImpl implements BackgroundService {

    private BackgroundRepository backgroundRepository;

    @Autowired
    public BackgroundServiceImpl(BackgroundRepository backgroundRepository) {
        this.backgroundRepository = backgroundRepository;
    }

    @Override
    public BackgroundImageHrefs getBackgroundImageHrefs() {
        return backgroundRepository.findById(BackgroundImageHrefs.SINGLETON_RECORD_ID)
                .orElseGet(this::createAndSaveEmptyBackgroundsObject);
    }

    @Override
    public void update(BackgroundImageHrefs backgroundImageHrefs) {
        backgroundRepository.save(backgroundImageHrefs);
    }

    private BackgroundImageHrefs createAndSaveEmptyBackgroundsObject() {
        return backgroundRepository.save(BackgroundImageHrefs.builder().build());
    }

}
