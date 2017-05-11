package crawler.web;

/**
 * Created by matvii on 09.04.17.
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// urls for the spider to crawl
public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;
    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";
    private String url;
    private String method;
    private HTTPRequestBody requestBody;
    private Map<String, Object> extraInfo;
    private Map<String, String> cookies = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private long priority;

    public Request() {}

    public Request(String url) {
        this.url = url;
    }

    public long getPriority() {
        return priority;
    }

    
    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    public Object getExtra(String key) {
        if (extraInfo == null) {
            return null;
        }
        return extraInfo.get(key);
    }

    public Request putExtra(String key, Object value) {
        if (extraInfo == null) {
            extraInfo = new HashMap<String, Object>();
        }
        extraInfo.put(key, value);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // HTTP method
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (url != null ? !url.equals(request.url) : request.url != null) return false;
        return method != null ? method.equals(request.method) : request.method == null;
    }

    public Request addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public Request addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HTTPRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(HTTPRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", extraInfo=" + extraInfo +
                ", priority=" + priority +
                ", headers=" + headers +
                ", cookies="+ cookies+
                '}';
    }

}

