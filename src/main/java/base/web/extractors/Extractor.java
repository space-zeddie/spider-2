package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import java.util.List;

/**
 * Extractor(extractor) for text.<br>
 *
 */
public interface Extractor {

    /**
     * Extract single result in text.<br>
     * If there are more than one result, only the first will be chosen.
     *
     * @param text text
     * @return result
     */
    String select(String text);

    /**
     * Extract all results in text.<br>
     *
     * @param text text
     * @return results
     */
    List<String> selectList(String text);

}
