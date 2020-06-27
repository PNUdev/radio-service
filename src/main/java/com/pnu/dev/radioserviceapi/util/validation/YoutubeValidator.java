package com.pnu.dev.radioserviceapi.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeValidator {

    private final static String youtubeLinkRegex =
            "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";

    public static Matcher matchYoutubeLink(String link) {
        Pattern youtubeLinkPattern = Pattern.compile(youtubeLinkRegex);
        return youtubeLinkPattern.matcher(link);
    }
}
