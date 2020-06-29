package com.pnu.dev.radioserviceapi.controller.api;

import com.pnu.dev.radioserviceapi.dto.response.PageResponse;
import com.pnu.dev.radioserviceapi.dto.response.ProgramDto;
import com.pnu.dev.radioserviceapi.service.ProgramApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/programs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProgramApiController {

    private ProgramApiService programApiService;

    @Autowired
    public ProgramApiController(ProgramApiService programApiService) {
        this.programApiService = programApiService;
    }

    @GetMapping
    public PageResponse findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                        Pageable pageable) {
        return programApiService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProgramDto findById(@PathVariable("id") String id) {
        return programApiService.findById(id);
    }

    @GetMapping("/search")
    public PageResponse searchByTitle(@RequestParam("q") String query,
                                      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        return programApiService.findByTitleContains(query, pageable);
    }

}
