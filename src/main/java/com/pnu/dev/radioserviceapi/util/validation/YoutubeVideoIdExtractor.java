package com.pnu.dev.radioserviceapi.util.validation;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YoutubeVideoIdExtractor {

    private final static String YOUTUBE_LINK_REGEX =
            "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";

    public Optional<String> getVideoIdFromLink(String link) {
        Pattern youtubeLinkPattern = Pattern.compile(YOUTUBE_LINK_REGEX);
        Matcher matcher = youtubeLinkPattern.matcher(link);
        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }
}
