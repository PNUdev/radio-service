package com.pnu.dev.radioserviceapi.util.validation;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class ValidationResult {

    private String errorMessage;

    public boolean isError() {
        return StringUtils.isNotBlank(errorMessage);
    }

    public static ValidationResult valid() {
        return ValidationResult.builder().build();
    }

    public static ValidationResult withError(String errorMessage) {
        return ValidationResult.builder()
                .errorMessage(errorMessage)
                .build();
    }

}