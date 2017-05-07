package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */

import java.util.List;

/**
 * Extractable text.<br>
 *
 */
public interface Extractable {

    /**
     * extract list with xpath
     *
     * @param xpath xpath
     * @return new Extractable after extract
     */
    Extractable xpath(String xpath);

    /**
     * extract list with css selector
     *
     * @param selector css selector expression
     * @return new Extractable after extract
     */
    public Extractable $(String selector);

    /**
     * extract list with css selector
     *
     * @param selector css selector expression
     * @param attrName attribute name of css selector
     * @return new Extractable after extract
     */
    public Extractable $(String selector, String attrName);

    /**
     * extract list with css selector
     *
     * @param selector css selector expression
     * @return new Extractable after extract
     */
    public Extractable css(String selector);

    /**
     * extract list with css selector
     *
     * @param selector css selector expression
     * @param attrName attribute name of css selector
     * @return new Extractable after extract
     */
    public Extractable css(String selector, String attrName);

    /**
     * extract smart content with ReadAbility algorithm
     *
     * @return content
     */
    public Extractable smartContent();

    /**
     * extract all links
     *
     * @return all links
     */
    public Extractable links();

    /**
     * extract list with regex, default group is group 1
     *
     * @param regex regex
     * @return new Extractable after extract
     */
    public Extractable regex(String regex);

    /**
     * extract list with regex
     *
     * @param regex regex
     * @param group group
     * @return new Extractable after extract
     */
    public Extractable regex(String regex, int group);

    /**
     * replace with regex
     *
     * @param regex regex
     * @param replacement replacement
     * @return new Extractable after extract
     */
    public Extractable replace(String regex, String replacement);

    /**
     * single string result
     *
     * @return single string result
     */
    public String toString();

    /**
     * single string result
     *
     * @return single string result
     */
    public String get();

    /**
     * if result exist for extract
     *
     * @return true if result exist
     */
    public boolean match();

    /**
     * multi string result
     *
     * @return multi string result
     */
    public List<String> all();

    /**
     * extract by JSON Path expression
     *
     * @param jsonPath jsonPath
     * @return result
     */
    public Extractable jsonPath(String jsonPath);

    /**
     * extract by custom extractor
     *
     * @param extractor extractor
     * @return result
     */
    public Extractable extract(Extractor extractor);

    /**
     * extract by custom extractor
     *
     * @param extractor extractor
     * @return result
     */
    public Extractable selectList(Extractor extractor);

    /**
     * get all nodes
     * @return result
     */
    public List<Extractable> nodes();
}
