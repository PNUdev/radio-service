package com.pnu.dev.radioserviceapi.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum BannerType {

    MAIN("Головний банер", "main-banner"),
    SECONDARY("Другорядний банер", "secondary-banner"),
    ACCESSORY("Допоміжний банер", "accessory-banner");

    private String bannerTypeName;

    private String bannerTypeValue;

    public static Optional<BannerType> findByBannerTypeValue(String bannerTypeValue) {
        return Stream.of(BannerType.values())
                .filter(bannerType -> StringUtils.equals(bannerType.getBannerTypeValue(), bannerTypeValue))
                .findFirst();
    }

}
