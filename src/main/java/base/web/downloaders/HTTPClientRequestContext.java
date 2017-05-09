package base.web.downloaders;

/**
 * Created by matvii on 07.05.17.
 */

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

public class HTTPClientRequestContext {

    private HttpUriRequest httpUriRequest;
    private HttpClientContext httpClientContext;

    public HttpUriRequest getHttpUriRequest() {
        return httpUriRequest;
    }

    public void setHttpUriRequest(HttpUriRequest httpUriRequest) {
        this.httpUriRequest = httpUriRequest;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public void setHttpClientContext(HttpClientContext httpClientContext) {
        this.httpClientContext = httpClientContext;
    }

}
