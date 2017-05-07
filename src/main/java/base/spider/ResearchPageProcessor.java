package base.spider;

import base.PageProcessor;
import base.web.Page;
import base.web.Site;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by matvii on 07.05.17.
 */
public class ResearchPageProcessor implements PageProcessor {

    private Site site = Site.instance()
            .setRetryTimes(Constants.RETRY_TIMES)
            .setSleepTime(Constants.SLEEP_TIME)
            .setTimeOut(Constants.TIME_OUT);

    @Override
    public void process(Page page) {
        // higher priority to .pdf links, since they most likely contain the papers themselves
        page.addTargetRequests(page.getHtml().links().regex("https://*\\.pdf").all(), 10);
        page.addTargetRequests(page.getHtml().links().regex("http://*\\.pdf").all(), 10);
        // TO DO: non-pdf linksg

        page.putField("paper", page.getUrl().regex("https://*\\.pdf").toString());
        page.putField("links", page.getHtml().$("a", "href").all());
        if (page.getResultItems().get("paper")==null || page.getResultItems().get("links")==null
                || ((List<String>)page.getResultItems().get("links")).size()==0)
            page.setSkip(true);
    }

    @Override
    public Site getSite() {
        return null;
    }
}