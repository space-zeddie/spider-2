package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */

import java.util.List;

public interface Extractable {

    Extractable $(String selector);

    Extractable $(String selector, String attrName);

    Extractable css(String selector);

    Extractable css(String selector, String attrName);

    Extractable smartContent();

    Extractable links();

    Extractable regex(String regex);

    Extractable regex(String regex, int group);

    Extractable replace(String regex, String replacement);

    String toString();

    String get();

    boolean match();

    List<String> all();

    Extractable jsonPath(String jsonPath);

    Extractable extract(Extractor extractor);

    Extractable selectList(Extractor extractor);

    List<Extractable> nodes();
}
