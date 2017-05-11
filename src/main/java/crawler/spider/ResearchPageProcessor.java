package crawler.spider;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.IPageProcessor;
import crawler.utils.CyrillicUtils;
import crawler.utils.UrlUtils;
import crawler.web.Page;
import crawler.web.Site;
import crawler.web.extractors.IExtractable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

import static org.apache.commons.io.FileUtils.getFile;

public class ResearchPageProcessor implements IPageProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Site site = Site.instance()
            .setRetryTimes(SpiderConstants.RETRY_TIMES)
            .setSleepTime(SpiderConstants.SLEEP_TIME)
            .setTimeOut(SpiderConstants.TIME_OUT);

    @Override
    public void process(Page page) {
        String charset = UrlUtils.getCharset(page.getHeaders().get("Content-Type").get(0));
        site.setCharset(charset);
        page.addTargetRequests(page.getHtml().links().regex(SpiderConstants.PDF_LINK_PATTERN).all(), 10);
        page.putField("paper_link", page.getUrl().regex(SpiderConstants.PDF_LINK_PATTERN).toString());
        IExtractable regex_url = page.getUrl().regex(SpiderConstants.PDF_LINK_PATTERN);
        if (regex_url.toString() != null) {
            String[] parts = (regex_url.toString().split("/"));
            page.putField("file_name", parts[parts.length - 1]);
            try {
                if (charset!=null) {
                    File f = new File("/home/matvii/Jun-KMA/1/", "raw_" + parts[parts.length - 1] + ".txt");
                    File f1 = new File("/home/matvii/Jun-KMA/1/", "html_" + parts[parts.length - 1] + ".txt");
                    OutputStreamWriter printWriter = new OutputStreamWriter(new FileOutputStream(f), Charset.forName(charset));
                    printWriter.write(page.getRawText());
                    printWriter.close();
                    printWriter = new OutputStreamWriter(new FileOutputStream(f1), Charset.forName(charset));
                   // page.getHtml().
                    //for (String s : page.getHtml().all())
                       // printWriter.write(page.getHtml().getFirstSourceText().);

                    logger.warn(getKeywords(page));
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            page.putField("file_name", null);
        }
        Date date = new Date();
        page.putField("retrieval_date", date.toString());
        if (page.getResultItems().get("paper")==null && page.getResultItems().get("file_name")==null)
            page.setSkip(true);
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String getKeywords(Page page) throws UnsupportedEncodingException {
        String c1 = "Ключові\nслова:\n";
        String c1_utf8 = CyrillicUtils.convertToUTF8(c1);
        String c2_utf8 = "";
       // logger.warn(CyrillicUtils.convertToUTF8(page.getHtml().toString()));
        //for (String s : page.getHtml().css("div").all()) {
            //logger.debug(s);
        String s = page.getHtml().toString();
            c2_utf8 += CyrillicUtils.convertToUTF8(s);
       // }
        //System.out.println(c2_utf8);
        int i1 = c2_utf8.indexOf(c1_utf8) + c1_utf8.length();
        int i2 = c2_utf8.indexOf(CyrillicUtils.convertToUTF8("."), i1);
        return (c2_utf8.substring(i1, i2));
        //return c2_utf8;

    }
}
