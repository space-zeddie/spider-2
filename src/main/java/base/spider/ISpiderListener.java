package base.spider;

import base.web.Request;

/**
 * Created by matvii on 07.05.17.
 */
public interface ISpiderListener {
    void onSuccess(Request request);
    void onError(Request request);
}
