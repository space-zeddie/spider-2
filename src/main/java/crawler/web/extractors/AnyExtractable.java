package crawler.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AnyExtractable implements IExtractable {

    protected abstract List<String> getSourceTexts();

    protected IExtractable select(IExtractor extractor, List<String> strings) {
        List<String> results = new ArrayList<String>();
        for (String string : strings) {
            String result = extractor.select(string);
            if (result != null) {
                results.add(result);
            }
        }
        return new PlainText(results);
    }

    protected IExtractable selectList(IExtractor extractor, List<String> strings) {
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
    public String get() {
        if (CollectionUtils.isNotEmpty(all())) {
            return all().get(0);
        } else {
            return null;
        }
    }

    @Override
    public IExtractable extract(IExtractor extractor) {
        return select(extractor, getSourceTexts());
    }

    @Override
    public IExtractable selectList(IExtractor extractor) {
        return selectList(extractor, getSourceTexts());
    }

    @Override
    public IExtractable regex(String regex) {
        RegexExtractor regexExtractor = Extractors.regex(regex);
        return selectList(regexExtractor, getSourceTexts());
    }

    @Override
    public IExtractable regex(String regex, int group) {
        RegexExtractor regexExtractor = Extractors.regex(regex, group);
        return selectList(regexExtractor, getSourceTexts());
    }

    @Override
    public IExtractable replace(String regex, String replacement) {
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

