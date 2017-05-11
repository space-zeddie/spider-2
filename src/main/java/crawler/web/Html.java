package crawler.web;

/**
 * Created by matvii on 10.04.17.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class Html extends HtmlNode {

    private static volatile boolean ESCAPED = false;

    private Document document;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Html(String text, String url) {
        try {
            disableJsoupEscape();
            this.document = Jsoup.parse(text, url);
        } catch (Exception e) {
            this.document = null;
            logger.warn("parse document error ", e);
        }
    }

    public Html(String text) {
        try {
            disableJsoupEscape();
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


    private void disableJsoupEscape() {
        if (!ESCAPED) {
            Entities.EscapeMode.base.getMap().clear();
            Entities.EscapeMode.extended.getMap().clear();
            Entities.EscapeMode.xhtml.getMap().clear();
            ESCAPED = true;
        }
    }

}
