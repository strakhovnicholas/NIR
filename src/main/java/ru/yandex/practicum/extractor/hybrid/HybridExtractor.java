package ru.yandex.practicum.extractor.hybrid;

import ru.yandex.practicum.extractor.ExtractedItem;
import ru.yandex.practicum.extractor.Extractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HybridExtractor implements Extractor {

    private final Extractor primary;
    private final Extractor secondary;

    public HybridExtractor(Extractor primary, Extractor secondary) {
        this.primary = Objects.requireNonNull(primary);
        this.secondary = Objects.requireNonNull(secondary);
    }

    @Override
    public List<ExtractedItem> extract(String text) {
        List<ExtractedItem> primaryItems = primary.extract(text);
        List<ExtractedItem> secondaryItems = secondary.extract(text);

        Set<String> seen = new HashSet<>();
        List<ExtractedItem> merged = new ArrayList<>();

        for (ExtractedItem item : primaryItems) {
            String key = item.getType() + "|" + item.getValue();
            seen.add(key);
            merged.add(item);
        }

        for (ExtractedItem item : secondaryItems) {
            String key = item.getType() + "|" + item.getValue();
            if (seen.contains(key)) {

                merged.add(new ExtractedItem(
                        item.getType(),
                        item.getValue(),
                        item.getStartOffset(),
                        item.getEndOffset(),
                        Math.min(1.0, item.getConfidence() + 0.2)
                ));
            } else {
                merged.add(item);
            }
        }

        return merged;
    }
}


