package base;
/**
 * Created by matvii on 08.04.17.
 */
import base.web.Page;
import base.web.Site;

public interface IPageProcessor {

    void process(Page page);

    Site getSite();
}