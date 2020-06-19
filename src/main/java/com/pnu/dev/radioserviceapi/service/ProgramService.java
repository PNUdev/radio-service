package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProgramService {

    Page<Program> findAll(Pageable pageable);

}
