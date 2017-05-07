package base.web.extractors;

/**
 * Created by matvii on 11.04.17.
 */
import org.jsoup.nodes.Element;

import java.util.List;

public interface ElementExtractor {

    /**
     * Extract single result in text.<br>
     * If there are more than one result, only the first will be chosen.
     *
     * @param element element
     * @return result
     */
    String select(Element element);

    /**
     * Extract all results in text.<br>
     *
     * @param element element
     * @return results
     */
    List<String> selectList(Element element);

}
