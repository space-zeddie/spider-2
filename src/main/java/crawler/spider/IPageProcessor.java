package crawler.spider;
/**
 * Created by matvii on 08.04.17.
 */
import crawler.web.Page;
import crawler.web.Site;

public interface IPageProcessor {

    void process(Page page);

    Site getSite();
}