package ru.yandex.practicum.extractor;

import lombok.Data;


@Data
public class ExtractedItem {

    private final String type;

    private final String value;

    private final int startOffset;

    private final int endOffset;

    private final double confidence;

    public ExtractedItem(String type, String value) {
        this(type, value, -1, -1, 1.0);
    }

    public ExtractedItem(String type, String value, int startOffset, int endOffset, double confidence) {
        this.type = type;
        this.value = value;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.confidence = confidence;
    }
}


