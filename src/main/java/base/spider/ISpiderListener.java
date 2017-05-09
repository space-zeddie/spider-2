package base.spider;

/**
 * Created by matvii on 07.05.17.
 */

import base.web.Request;

public interface ISpiderListener {
    void onSuccess(Request request);
    void onError(Request request);
}
