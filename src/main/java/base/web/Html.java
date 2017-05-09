package base.web;

/**
 * Created by matvii on 10.04.17.
 */
import base.web.extractors.IElementExtractor;
import base.web.extractors.IExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class Html extends HtmlNode {

    private static volatile boolean INITED = false;
    public static boolean DISABLE_HTML_ENTITY_ESCAPE = false;

    private Document document;
    private Logger logger = LoggerFactory.getLogger(getClass());


    private void disableJsoupHtmlEntityEscape() {
        if (DISABLE_HTML_ENTITY_ESCAPE && !INITED) {
            Entities.EscapeMode.base.getMap().clear();
            Entities.EscapeMode.extended.getMap().clear();
            Entities.EscapeMode.xhtml.getMap().clear();
            INITED = true;
        }
    }

    public Html(String text, String url) {
        try {
            disableJsoupHtmlEntityEscape();
            this.document = Jsoup.parse(text, url);
        } catch (Exception e) {
            this.document = null;
            logger.warn("parse document error ", e);
        }
    }

    public Html(String text) {
        try {
            disableJsoupHtmlEntityEscape();
            this.document = Jsoup.parse(text);
        } catch (Exception e) {
            this.document = null;
            logger.warn("parse document error ", e);
        }
    }

    public Html(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    protected List<Element> getElements() {
        return Collections.<Element>singletonList(getDocument());
    }

    /**
     * @param extractor extractor
     * @return result
     */
    public String selectDocument(IExtractor extractor) {
        if (extractor instanceof IElementExtractor) {
            IElementExtractor elementExtractor = (IElementExtractor) extractor;
            return elementExtractor.select(getDocument());
        } else {
            return extractor.select(getFirstSourceText());
        }
    }

    public List<String> selectDocumentForList(IExtractor extractor) {
        if (extractor instanceof IElementExtractor) {
            IElementExtractor elementExtractor = (IElementExtractor) extractor;
            return elementExtractor.selectList(getDocument());
        } else {
            return extractor.selectList(getFirstSourceText());
        }
    }

    public static Html create(String text) {
        return new Html(text);
    }

}
