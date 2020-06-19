package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.mongo.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProgramService {

    Page<Program> findAll(Pageable pageable);

    Optional<Program> findById(String id);

}
