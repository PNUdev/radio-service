package com.pnu.dev.radioserviceapi.controller.admin;

import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/programs")
public class ProgramAdminController {

    private ProgramService programService;

    @Autowired
    public ProgramAdminController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public String listPrograms(Model model, @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<Program> programsPage = programService.findAll(pageable);
        model.addAttribute("programsPage", programsPage);

        return "programs/index";
    }

    @GetMapping("/new")
    public String create() {
        return "programs/form";
    }

    // 1. create program (should have title, description and image (compress it and store to mongo))
    // 2. update program
    // 3. delete program
    // 4. list all programs
}
