package crawler.web.downloaders;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.web.Html;
import crawler.web.Page;
import crawler.web.Request;
import crawler.web.Site;

public abstract class AbstractDownloader implements IDownloader {

    public Html download(String url) {
        return download(url, null);
    }

    public Html download(String url, String charset) {
        Page page = download(new Request(url), Site.instance().setCharset(charset).toTask());
        return (Html) page.getHtml();
    }

    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }

}

