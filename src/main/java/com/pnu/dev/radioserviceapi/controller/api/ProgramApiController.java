package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.dto.response.ProgramsPageResponse;
import com.pnu.dev.radioserviceapi.service.ProgramApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/programs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProgramApiController {

    private ProgramApiService programApiService;

    @Autowired
    public ProgramApiController(ProgramApiService programApiService) {
        this.programApiService = programApiService;
    }

    @GetMapping
    public ProgramsPageResponse findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                Pageable pageable) {
        return programApiService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProgramDto findById(@PathVariable("id") String id) {
        return programApiService.findById(id);
    }

    @GetMapping("/search")
    public ProgramsPageResponse searchByTitle(@RequestParam("q") String query,
                                              @PageableDefault(size = 10) Pageable pageable) {
        return programApiService.searchByTitle(query, pageable);
    }

}
