package base.web;

/**
 * Created by matvii on 10.04.17.
 */
import base.web.extractors.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HtmlNode extends AnyExtractable {

    private final List<Element> elements;

    public HtmlNode(List<Element> elements) {
        this.elements = elements;
    }

    public HtmlNode() {
        elements = null;
    }

    protected List<Element> getElements() {
        return elements;
    }

    @Override
    public IExtractable links() {
        return extractElements(new LinksExtractor());
    }

    @Override
    public IExtractable selectList(IExtractor extractor) {
        if (extractor instanceof BasicElementExtractor) {
            return extractElements((BasicElementExtractor) extractor);
        }
        return selectList(extractor, getSourceTexts());
    }

    @Override
    public IExtractable extract(IExtractor extractor) {
        return selectList(extractor);
    }

    /**
     * extract elements
     *
     * @param elementSelector elementSelector
     * @return result
     */
    protected IExtractable extractElements(BasicElementExtractor elementSelector) {
        ListIterator<Element> elementIterator = getElements().listIterator();
        if (!elementSelector.hasAttribute()) {
            List<Element> resultElements = new ArrayList<Element>();
            while (elementIterator.hasNext()) {
                Element element = checkElementAndConvert(elementIterator);
                List<Element> selectElements = elementSelector.selectElements(element);
                resultElements.addAll(selectElements);
            }
            return new HtmlNode(resultElements);
        } else {
            // has attribute, consider as plaintext
            List<String> resultStrings = new ArrayList<String>();
            while (elementIterator.hasNext()) {
                Element element = checkElementAndConvert(elementIterator);
                List<String> selectList = elementSelector.selectList(element);
                resultStrings.addAll(selectList);
            }
            return new PlainText(resultStrings);

        }
    }

    /**
     * Only document can be extract
     * See: https://github.com/code4craft/webmagic/issues/113
     *
     * @param elementIterator elementIterator
     * @return element element
     */
    private Element checkElementAndConvert(ListIterator<Element> elementIterator) {
        Element element = elementIterator.next();
        if (!(element instanceof Document)) {
            Document root = new Document(element.ownerDocument().baseUri());
            Element clone = element.clone();
            root.appendChild(clone);
            elementIterator.set(root);
            return root;
        }
        return element;
    }

    @Override
    public IExtractable css(String selector) {
        CssExtractor cssSelector = Extractors.$(selector);
        return extractElements(cssSelector);
    }

    @Override
    public IExtractable css(String selector, String attrName) {
        CssExtractor cssSelector = Extractors.$(selector, attrName);
        return extractElements(cssSelector);
    }

    @Override
    public List<IExtractable> nodes() {
        List<IExtractable> extractables = new ArrayList<IExtractable>();
        for (Element element : getElements()) {
            List<Element> childElements = new ArrayList<Element>(1);
            childElements.add(element);
            extractables.add(new HtmlNode(childElements));
        }
        return extractables;
    }

    @Override
    protected List<String> getSourceTexts() {
        List<String> sourceTexts = new ArrayList<String>(getElements().size());
        for (Element element : getElements()) {
            sourceTexts.add(element.toString());
        }
        return sourceTexts;
    }
}
