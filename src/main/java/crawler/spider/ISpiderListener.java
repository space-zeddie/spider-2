package crawler.spider;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.web.Request;

public interface ISpiderListener {
    void onSuccess(Request request);
    void onError(Request request);
}
