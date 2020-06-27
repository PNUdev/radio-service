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

    private String humanReadableName;

    private String value;

    DayOfWeek(String humanReadableName, String value) {
        this.humanReadableName = humanReadableName;
        this.value = value;
    }

    public static Optional<DayOfWeek> findByValue(String value) {
        return Stream.of(DayOfWeek.values())
                .filter(dayOfWeek -> StringUtils.equals(dayOfWeek.getValue(), value))
                .findFirst();
    }
}
