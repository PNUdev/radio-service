package com.pnu.dev.radioserviceapi.mongo;

import com.pnu.dev.radioserviceapi.dto.response.DayOfWeekResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DayOfWeek {

    SUNDAY("Неділя", "sunday"),
    MONDAY("Понеділок", "monday"),
    TUESDAY("Вівторок", "tuesday"),
    WEDNESDAY("Середа", "wednesday"),
    THURSDAY("Четвер", "thursday"),
    FRIDAY("П'ятниця", "friday"),
    SATURDAY("Субота", "saturday");

    private String valueUkr;

    private String urlValue;

    public static Optional<DayOfWeek> findByUrlValue(String value) {
        return Stream.of(DayOfWeek.values())
                .filter(dayOfWeek -> StringUtils.equals(dayOfWeek.getUrlValue(), value))
                .findFirst();
    }

    public DayOfWeekResponse toDayOfWeekResponse() {
        return DayOfWeekResponse.builder()
                .urlValue(this.urlValue)
                .nameUkr(this.valueUkr)
                .build();
    }
}
