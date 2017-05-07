package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafer@gmail.com
 * @since 0.5.2
 */
public abstract class AnyExtractable implements Extractable {

    protected abstract List<String> getSourceTexts();

    @Override
    public Extractable css(String extractor) {
        return $(extractor);
    }

    @Override
    public Extractable css(String extractor, String attrName) {
        return $(extractor, attrName);
    }

    protected Extractable select(Extractor extractor, List<String> strings) {
        List<String> results = new ArrayList<String>();
        for (String string : strings) {
            String result = extractor.select(string);
            if (result != null) {
                results.add(result);
            }
        }
        return new PlainText(results);
    }

    protected Extractable selectList(Extractor extractor, List<String> strings) {
        List<String> results = new ArrayList<String>();
        for (String string : strings) {
            List<String> result = extractor.selectList(string);
            results.addAll(result);
        }
        return new PlainText(results);
    }

    @Override
    public List<String> all() {
        return getSourceTexts();
    }

    @Override
    public Extractable jsonPath(String jsonPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get() {
        if (CollectionUtils.isNotEmpty(all())) {
            return all().get(0);
        } else {
            return null;
        }
    }

    @Override
    public Extractable extract(Extractor extractor) {
        return select(extractor, getSourceTexts());
    }

    @Override
    public Extractable selectList(Extractor extractor) {
        return selectList(extractor, getSourceTexts());
    }

    @Override
    public Extractable regex(String regex) {
        RegexExtractor regexExtractor = Extractors.regex(regex);
        return selectList(regexExtractor, getSourceTexts());
    }

    @Override
    public Extractable regex(String regex, int group) {
        RegexExtractor regexExtractor = Extractors.regex(regex, group);
        return selectList(regexExtractor, getSourceTexts());
    }

    @Override
    public Extractable replace(String regex, String replacement) {
        ReplaceSelector replaceSelector = new ReplaceSelector(regex,replacement);
        return select(replaceSelector, getSourceTexts());
    }

    public String getFirstSourceText() {
        if (getSourceTexts() != null && getSourceTexts().size() > 0) {
            return getSourceTexts().get(0);
        }
        return null;
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    public boolean match() {
        return getSourceTexts() != null && getSourceTexts().size() > 0;
    }
}

