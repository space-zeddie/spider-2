package base.web.downloaders;

import base.web.Html;
import base.web.Page;
import base.web.Request;
import base.web.Site;

/**
 * Created by matvii on 07.05.17.
 */
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

