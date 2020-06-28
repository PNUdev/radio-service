package com.pnu.dev.radioserviceapi.mongo;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum DayOfWeek {

    SUNDAY("Неділя", "sunday"),
    MONDAY("Понеділок", "monday"),
    TUESDAY("Вівторок", "tuesday"),
    WEDNESDAY("Середа", "wednesday"),
    THURSDAY("Четвер", "thursday"),
    FRIDAY("П'ятниця", "friday"),
    SATURDAY("Субота", "saturday");

    private String valueUkr;

    private String valueEng;

    DayOfWeek(String valueUkr, String valueEng) {
        this.valueUkr = valueUkr;
        this.valueEng = valueEng;
    }

    public static Optional<DayOfWeek> findByValueEng(String value) {
        return Stream.of(DayOfWeek.values())
                .filter(dayOfWeek -> StringUtils.equals(dayOfWeek.getValueEng(), value))
                .findFirst();
    }
}
