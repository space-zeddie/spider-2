package base.web;

/**
 * Created by matvii on 10.04.17.
 */
import base.web.selectors.ElementExtractor;
import base.web.selectors.Extractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Extractable html.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class Html extends HtmlNode {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static volatile boolean INITED = false;

    /**
     * Disable jsoup html entity escape. It can be set just before any Html instance is created.
     */
    public static boolean DISABLE_HTML_ENTITY_ESCAPE = false;

    /**
     * Disable jsoup html entity escape. It is a hack way only for jsoup 1.7.2.
     */
    private void disableJsoupHtmlEntityEscape() {
        if (DISABLE_HTML_ENTITY_ESCAPE && !INITED) {
            Entities.EscapeMode.base.getMap().clear();
            Entities.EscapeMode.extended.getMap().clear();
            Entities.EscapeMode.xhtml.getMap().clear();
            INITED = true;
        }
    }

    /**
     * Store parsed document for better performance when only one text exist.
     */
    private Document document;

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
    public String selectDocument(Extractor extractor) {
        if (extractor instanceof ElementExtractor) {
            ElementExtractor elementExtractor = (ElementExtractor) extractor;
            return elementExtractor.select(getDocument());
        } else {
            return extractor.select(getFirstSourceText());
        }
    }

    public List<String> selectDocumentForList(Extractor extractor) {
        if (extractor instanceof ElementExtractor) {
            ElementExtractor elementExtractor = (ElementExtractor) extractor;
            return elementExtractor.selectList(getDocument());
        } else {
            return extractor.selectList(getFirstSourceText());
        }
    }

    public static Html create(String text) {
        return new Html(text);
    }

}
