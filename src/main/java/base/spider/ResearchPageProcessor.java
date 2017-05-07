package base.spider;

import base.PageProcessor;
import base.web.Page;
import base.web.Site;

/**
 * Created by matvii on 07.05.17.
 */
public class ResearchPageProcessor implements PageProcessor {

    private Site site = Site.instance().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }
}
