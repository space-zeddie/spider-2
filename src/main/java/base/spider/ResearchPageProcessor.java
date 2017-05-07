package base.spider;

import base.IPageProcessor;
import base.web.Page;
import base.web.Site;
import base.web.extractors.Extractable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matvii on 07.05.17.
 */
public class ResearchPageProcessor implements IPageProcessor {

    private Site site = Site.instance()
            .setRetryTimes(Constants.RETRY_TIMES)
            .setSleepTime(Constants.SLEEP_TIME)
            .setTimeOut(Constants.TIME_OUT);

    @Override
    public void process(Page page) {
        String[] header = page.getHeaders().get("Content-Type").get(0).split(";charset=");
        if (header.length > 1) {
            //System.out.println(header[1]);
            site.setCharset(header[1]);
        }

          // higher priority to .pdf links, since they most likely contain the papers themselves
        page.addTargetRequests(page.getHtml().links().regex(Constants.PDF_LINK_PATTERN).all(), 10);
        /*for (String link : page.getHtml().links()
                .regex(Constants.PDF_LINK_PATTERN).all()) {
            System.out.println(link);
        }*/
        //System.out.println(page.getUrl().regex(Constants.PDF_LINK_PATTERN).toString());
        // TO DO: non-pdf linksg

        page.putField("paper_link", page.getUrl().regex(Constants.PDF_LINK_PATTERN).toString());
        Extractable regex_url = page.getUrl().regex(Constants.PDF_LINK_PATTERN);
        if (regex_url.toString() != null) {
            String[] parts = (regex_url.toString().split("/"));
            //System.err.println(parts[parts.length - 1]);
            page.putField("paper_name", parts[parts.length - 1]);
        } else {
            page.putField("paper_name", null);
        }
        page.putField("links", page.getHtml().links().all());
        if (page.getResultItems().get("paper")==null && page.getResultItems().get("paper_name")==null
                && ((List<String>)page.getResultItems().get("links")).size()==0)
            page.setSkip(true);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
