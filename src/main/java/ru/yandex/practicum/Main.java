package ru.yandex.practicum;

import ru.yandex.practicum.extractor.ExtractedItem;
import ru.yandex.practicum.extractor.Extractor;
import ru.yandex.practicum.extractor.RuleBasedExtractor;
import ru.yandex.practicum.extractor.opennlp.OpenNlpNerExtractor;
import ru.yandex.practicum.extractor.hybrid.HybridExtractor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = """
                Order from 12/03/2024 with total amount 15,000.50 USD.
                Client: Acme Corp.
                City: London.
                John Smith works at Contoso Ltd. in New York.
                """;

        // 1. Базовый rule-based метод
        Extractor ruleBased = new RuleBasedExtractor();
        List<ExtractedItem> ruleBasedItems = ruleBased.extract(text);
        System.out.println("=== Rule-based extractor ===");
        ruleBasedItems.forEach(System.out::println);

        // 2. NER на базе OpenNLP
        Extractor nerExtractor = new OpenNlpNerExtractor();
        List<ExtractedItem> nerItems = nerExtractor.extract(text);
        System.out.println("\n=== OpenNLP NER extractor ===");
        nerItems.forEach(System.out::println);

        // 3. Гибридный подход (NLP + правила)
        Extractor hybrid = new HybridExtractor(ruleBased, nerExtractor);
        List<ExtractedItem> hybridItems = hybrid.extract(text);
        System.out.println("\n=== Hybrid extractor ===");
        hybridItems.forEach(System.out::println);
    }
}