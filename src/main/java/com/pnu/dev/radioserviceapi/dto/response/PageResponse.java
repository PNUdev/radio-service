package com.pnu.dev.radioserviceapi.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private int pageNumber;

    private int totalPages;

    private List<T> content;
}
