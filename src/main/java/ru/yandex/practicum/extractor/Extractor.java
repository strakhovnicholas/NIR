package ru.yandex.practicum.extractor;

import java.util.List;


public interface Extractor {
    List<ExtractedItem> extract(String text);
}


