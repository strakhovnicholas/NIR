package ru.yandex.practicum.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleBasedExtractor implements Extractor {

    private static final Pattern DATE_PATTERN =
            Pattern.compile("\\b(\\d{2}/\\d{2}/\\d{4}|\\d{4}-\\d{2}-\\d{2})\\b");

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("\\b\\d{1,3}(?:[\\s\\u00A0]\\d{3})*(?:[\\.,]\\d+)?\\b");

    private static final Pattern ORG_PATTERN =
            Pattern.compile("\\b([A-Z][A-Za-z0-9& ]+\\s(?:Inc\\.|Ltd\\.|Corp\\.|Company))\\b");

    private static final Pattern CITY_PATTERN =
            Pattern.compile("\\bCity: ([A-Z][a-zA-Z]+)\\b");

    @Override
    public List<ExtractedItem> extract(String text) {
        List<ExtractedItem> result = new ArrayList<>();

        Matcher dateMatcher = DATE_PATTERN.matcher(text);
        while (dateMatcher.find()) {
            result.add(new ExtractedItem(
                    "DATE",
                    dateMatcher.group(),
                    dateMatcher.start(),
                    dateMatcher.end(),
                    1.0
            ));
        }

        Matcher numMatcher = NUMBER_PATTERN.matcher(text);
        while (numMatcher.find()) {
            result.add(new ExtractedItem(
                    "NUMBER",
                    numMatcher.group(),
                    numMatcher.start(),
                    numMatcher.end(),
                    1.0
            ));
        }

        Matcher orgMatcher = ORG_PATTERN.matcher(text);
        while (orgMatcher.find()) {
            result.add(new ExtractedItem(
                    "ORG",
                    orgMatcher.group(),
                    orgMatcher.start(),
                    orgMatcher.end(),
                    1.0
            ));
        }

        Matcher cityMatcher = CITY_PATTERN.matcher(text);
        while (cityMatcher.find()) {
            result.add(new ExtractedItem(
                    "CITY",
                    cityMatcher.group(1),
                    cityMatcher.start(1),
                    cityMatcher.end(1),
                    1.0
            ));
        }

        return result;
    }
}


