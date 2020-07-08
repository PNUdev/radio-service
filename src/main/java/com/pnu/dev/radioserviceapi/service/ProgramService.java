package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.ProgramForm;
import com.pnu.dev.radioserviceapi.mongo.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgramService {

    Page<Program> findAll(Pageable pageable);

    List<Program> findAll();

    Program findById(String id);

    void deleteById(String id);

    void create(ProgramForm programForm);

    void update(String id, ProgramForm programForm);

    Page<Program> findByTitleContains(String query, Pageable pageable);

}
