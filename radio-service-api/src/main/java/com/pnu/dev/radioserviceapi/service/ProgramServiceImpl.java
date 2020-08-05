package com.pnu.dev.radioserviceapi.service;

import com.pnu.dev.radioserviceapi.dto.form.ProgramForm;
import com.pnu.dev.radioserviceapi.exception.RadioServiceAdminException;
import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import com.pnu.dev.radioserviceapi.repository.ScheduleItemRepository;
import com.pnu.dev.radioserviceapi.util.validation.DataValidator;
import com.pnu.dev.radioserviceapi.util.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private ProgramRepository programRepository;

    private ScheduleItemRepository scheduleItemRepository;

    private DataValidator dataValidator;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository,
                              ScheduleItemRepository scheduleItemRepository,
                              DataValidator dataValidator) {
        this.programRepository = programRepository;
        this.scheduleItemRepository = scheduleItemRepository;
        this.dataValidator = dataValidator;
    }

    @Override
    public Page<Program> findAll(Pageable pageable) {
        return programRepository.findAll(pageable);
    }

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public Program findById(String id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new RadioServiceAdminException("Програму не знайдено!"));
    }

    @Override
    @Transactional
    public void deleteById(String id) {

        scheduleItemRepository.deleteAllByProgramId(id);

        programRepository.deleteById(id);
    }

    @Override
    public void create(ProgramForm programForm) {

        validateProgramForm(programForm);

        Program program = Program.builder()
                .title(programForm.getTitle())
                .description(programForm.getDescription())
                .imageUrl(programForm.getImageUrl())
                .build();

        programRepository.save(program);
    }

    @Override
    public void update(String id, ProgramForm programForm) {

        validateProgramForm(programForm);

        Program program = findById(id);

        Program updatedProgram = program.toBuilder()
                .title(programForm.getTitle())
                .description(programForm.getDescription())
                .imageUrl(programForm.getImageUrl())
                .build();

        programRepository.save(updatedProgram);
    }

    @Override
    public Page<Program> findByTitleContains(String query, Pageable pageable) {
        return programRepository.findAllByTitleContainsIgnoreCase(query, pageable);
    }

    private void validateProgramForm(ProgramForm programForm) {
        ValidationResult validationResult = dataValidator.validate(programForm);

        if (validationResult.isError()) {
            throw new RadioServiceAdminException("Помилка валідації: " + validationResult.getErrorMessage());
        }
    }
}
