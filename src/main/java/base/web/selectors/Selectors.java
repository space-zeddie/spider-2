package base.web.selectors;

/**
 * Created by matvii on 19.04.17.
 */
public class Selectors {

    public static RegexSelector regex(String expr) {
        return new RegexSelector(expr);
    }

    public static RegexSelector regex(String expr, int group) {
        return new RegexSelector(expr,group);
    }

    public static SmartContentSelector smartContent() {
        return new SmartContentSelector();
    }

    public static CssSelector $(String expr) {
        return new CssSelector(expr);
    }

    public static CssSelector $(String expr, String attrName) {
        return new CssSelector(expr, attrName);
    }

    public static XpathSelector xpath(String expr) {
        return new XpathSelector(expr);
    }

    /**
     * @see #xpath(String)
     * @param expr expr
     * @return new selector
     */
    @Deprecated
    public static XpathSelector xsoup(String expr) {
        return new XpathSelector(expr);
    }

    public static AndSelector and(Selector... selectors) {
        return new AndSelector(selectors);
    }

    public static OrSelector or(Selector... selectors) {
        return new OrSelector(selectors);
    }

}
