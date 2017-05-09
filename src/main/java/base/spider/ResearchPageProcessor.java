package base.spider;

/**
 * Created by matvii on 07.05.17.
 */

import base.IPageProcessor;
import base.utils.CharsetUtils;
import base.utils.UrlUtils;
import base.web.Page;
import base.web.Site;
import base.web.extractors.IExtractable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ResearchPageProcessor implements IPageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Site site = Site.instance()
            .setRetryTimes(Constants.RETRY_TIMES)
            .setSleepTime(Constants.SLEEP_TIME)
            .setTimeOut(Constants.TIME_OUT);

    @Override
    public void process(Page page) {
        String charset = UrlUtils.getCharset(page.getHeaders().get("Content-Type").get(0));
        site.setCharset(charset);
        page.addTargetRequests(page.getHtml().links().regex(Constants.PDF_LINK_PATTERN).all(), 10);
        page.putField("paper_link", page.getUrl().regex(Constants.PDF_LINK_PATTERN).toString());
        IExtractable regex_url = page.getUrl().regex(Constants.PDF_LINK_PATTERN);
        if (regex_url.toString() != null) {
            String[] parts = (regex_url.toString().split("/"));
            //System.err.println(parts[parts.length - 1]);
            page.putField("paper_name", parts[parts.length - 1]);
        } else {
            page.putField("paper_name", null);
        }
        Date date = new Date();
        page.putField("retrieval_date", date.toString());
        if (page.getResultItems().get("paper")==null && page.getResultItems().get("paper_name")==null
                )
            page.setSkip(true);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
