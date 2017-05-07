package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Extractable plain text.<br>
 * Can not be selected by XPath or CSS Extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
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
    public Extractable xpath(String xpath) {
        throw new UnsupportedOperationException("XPath can not apply to plain text. Please check whether you use a previous xpath with attribute extract (/@href etc).");
    }

    @Override
    public Extractable $(String selector) {
        throw new UnsupportedOperationException("$ can not apply to plain text. Please check whether you use a previous xpath with attribute extract (/@href etc).");
    }

    @Override
    public Extractable $(String selector, String attrName) {
        throw new UnsupportedOperationException("$ can not apply to plain text. Please check whether you use a previous xpath with attribute extract (/@href etc).");
    }

    @Override
    public Extractable smartContent() {
        throw new UnsupportedOperationException("Smart content can not apply to plain text. Please check whether you use a previous xpath with attribute extract (/@href etc).");
    }

    @Override
    public Extractable links() {
        throw new UnsupportedOperationException("Links can not apply to plain text. Please check whether you use a previous xpath with attribute extract (/@href etc).");
    }

    @Override
    public List<Extractable> nodes() {
        List<Extractable> nodes = new ArrayList<Extractable>(getSourceTexts().size());
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