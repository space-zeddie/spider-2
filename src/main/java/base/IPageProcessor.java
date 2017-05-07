package base; /**
 * Created by matvii on 08.04.17.
 */
import base.web.Page;
import base.web.Site;

/**
 * Interface to be implemented to customize a crawler.<br>
 * <br>
 * In base.IPageProcessor, you can customize:
 * <br>
 * start urls and other settings in {@link Site}<br>
 * how the urls to fetch are detected               <br>
 * how the data are extracted and stored             <br>
 *
 */
public interface IPageProcessor {

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    void process(Page page);

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    Site getSite();
}