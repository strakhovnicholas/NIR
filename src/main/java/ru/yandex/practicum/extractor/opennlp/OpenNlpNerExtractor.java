package ru.yandex.practicum.extractor.opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import ru.yandex.practicum.extractor.ExtractedItem;
import ru.yandex.practicum.extractor.Extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OpenNlpNerExtractor implements Extractor {

    private final SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    private final NameFinderME personFinder;
    private final NameFinderME orgFinder;

    public OpenNlpNerExtractor() {
        this.personFinder = createNameFinder("/models/en-ner-person.bin");
        this.orgFinder = createNameFinder("/models/en-ner-organization.bin");
    }

    private NameFinderME createNameFinder(String modelPath) {
        try (InputStream modelStream = getClass().getResourceAsStream(modelPath)) {
            if (modelStream == null) {
                return null;
            }
            TokenNameFinderModel model = new TokenNameFinderModel(modelStream);
            return new NameFinderME(model);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExtractedItem> extract(String text) {
        List<ExtractedItem> result = new ArrayList<>();

        String[] tokens = tokenizer.tokenize(text);

        if (personFinder != null) {
            Span[] personSpans = personFinder.find(tokens);
            for (Span span : personSpans) {
                String value = joinTokens(tokens, span);
                result.add(new ExtractedItem("PERSON", value, -1, -1, span.getProb()));
            }
            personFinder.clearAdaptiveData();
        }

        if (orgFinder != null) {
            Span[] orgSpans = orgFinder.find(tokens);
            for (Span span : orgSpans) {
                String value = joinTokens(tokens, span);
                result.add(new ExtractedItem("ORG", value, -1, -1, span.getProb()));
            }
            orgFinder.clearAdaptiveData();
        }

        return result;
    }

    private String joinTokens(String[] tokens, Span span) {
        StringBuilder sb = new StringBuilder();
        for (int i = span.getStart(); i < span.getEnd(); i++) {
            if (i > span.getStart()) {
                sb.append(' ');
            }
            sb.append(tokens[i]);
        }
        return sb.toString();
    }
}


