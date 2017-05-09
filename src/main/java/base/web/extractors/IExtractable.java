package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */

import java.util.List;

public interface IExtractable {

    IExtractable css(String selector);

    IExtractable css(String selector, String attrName);

    IExtractable links();

    IExtractable regex(String regex);

    IExtractable regex(String regex, int group);

    IExtractable replace(String regex, String replacement);

    String toString();

    String get();

    boolean match();

    List<String> all();

    IExtractable extract(IExtractor extractor);

    IExtractable selectList(IExtractor extractor);

    List<IExtractable> nodes();
}
