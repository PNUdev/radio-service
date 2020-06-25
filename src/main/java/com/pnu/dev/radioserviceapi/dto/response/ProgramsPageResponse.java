package com.pnu.dev.radioserviceapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramsPageResponse {

    private int pageNumber;

    private int totalPages;

    private List<ProgramDto> programs;

}
