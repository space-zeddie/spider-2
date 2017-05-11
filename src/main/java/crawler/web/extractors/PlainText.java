package crawler.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */

import java.util.ArrayList;
import java.util.List;

public class PlainText extends AnyExtractable {

    protected List<String> sourceTexts;

    public PlainText(List<String> sourceTexts) {
        this.sourceTexts = sourceTexts;
    }

    public PlainText(String text) {
        this.sourceTexts = new ArrayList<String>();
        sourceTexts.add(text);
    }

    public static PlainText create(String text) {
        return new PlainText(text);
    }

    @Override
    public IExtractable css(String selector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IExtractable css(String selector, String attrName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IExtractable links() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IExtractable> nodes() {
        List<IExtractable> nodes = new ArrayList<IExtractable>(getSourceTexts().size());
        for (String string : getSourceTexts()) {
            nodes.add(PlainText.create(string));
        }
        return nodes;
    }

    @Override
    protected List<String> getSourceTexts() {
        return sourceTexts;
    }
}