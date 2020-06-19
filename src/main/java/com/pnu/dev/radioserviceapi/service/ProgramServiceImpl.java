package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProgramServiceImpl implements ProgramService {

    private ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public Page<Program> findAll(Pageable pageable) {
        return programRepository.findAll(pageable);
    }
}
