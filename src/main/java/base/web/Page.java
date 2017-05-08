package base.web; /**
 * Created by matvii on 08.04.17.
 */
import base.ResultItems;
import base.utils.UrlUtils;
import base.web.extractors.Extractable;
import org.apache.commons.lang3.StringUtils;
import base.utils.HTTPConsts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page {

    private Request request;

    private ResultItems resultItems = new ResultItems();

    private Html html;

    private String rawText;

    private Extractable url;

    private Map<String,List<String>> headers;

    private int statusCode = HTTPConsts.StatusCode.CODE_200;

    private boolean downloadSuccess = true;

    private List<Request> targetRequests = new ArrayList<Request>();

    public Page() {
    }

    public static Page fail(){
        Page page = new Page();
        page.setDownloadSuccess(false);
        return page;
    }

    public Page setSkip(boolean skip) {
        resultItems.setSkip(skip);
        return this;

    }


    // store extract results
    public void putField(String key, Object field) {
        resultItems.put(key, field);
    }

    public Html getHtml() {
        if (html == null) {
            html = new Html(rawText, request.getUrl());
        }
        return html;
    }


    public void setHtml(Html html) {
        this.html = html;
    }

    public List<Request> getTargetRequests() {
        return targetRequests;
    }

    public void addTargetRequests(List<String> requests) {
        for (String s : requests) {
            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            s = UrlUtils.canonicalizeUrl(s, url.toString());
            targetRequests.add(new Request(s));
        }
    }

    public void addTargetRequests(List<String> requests, long priority) {
        for (String s : requests) {
            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            s = UrlUtils.canonicalizeUrl(s, url.toString());
            targetRequests.add(new Request(s).setPriority(priority));
        }
    }

    public void addTargetRequest(String requestString) {
        if (StringUtils.isBlank(requestString) || requestString.equals("#")) {
            return;
        }
        requestString = UrlUtils.canonicalizeUrl(requestString, url.toString());
        targetRequests.add(new Request(requestString));
    }

    public void addTargetRequest(Request request) {
        targetRequests.add(request);
    }

    public Extractable getUrl() {
        return url;
    }

    public void setUrl(Extractable url) {
        this.url = url;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
        this.resultItems.setRequest(request);
    }

    public ResultItems getResultItems() {
        return resultItems;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRawText() {
        return rawText;
    }

    public Page setRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public boolean isDownloadSuccess() {
        return downloadSuccess;
    }

    public void setDownloadSuccess(boolean downloadSuccess) {
        this.downloadSuccess = downloadSuccess;
    }

    @Override
    public String toString() {
        return "base.web.Page{" +
                "request=" + request +
                ", resultItems=" + resultItems +
                ", html=" + html +
                ", rawText='" + rawText + '\'' +
                ", url=" + url +
                ", headers=" + headers +
                ", statusCode=" + statusCode +
                ", success=" + downloadSuccess +
                ", targetRequests=" + targetRequests +
                '}';
    }
}
