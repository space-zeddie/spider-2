package crawler.web.extractors;

/**
 * Created by matvii on 19.04.17.
 */
public class Extractors {

    public static RegexExtractor regex(String expr) {
        return new RegexExtractor(expr);
    }

    public static RegexExtractor regex(String expr, int group) {
        return new RegexExtractor(expr,group);
    }

    public static CssExtractor $(String expr) {
        return new CssExtractor(expr);
    }

    public static CssExtractor $(String expr, String attrName) {
        return new CssExtractor(expr, attrName);
    }

}
