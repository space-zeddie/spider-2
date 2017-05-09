package base.web.proxy;

/**
 * Created by matvii on 07.05.17.
 */

import base.Task;
import base.web.Page;

public interface IProxyProvider {

    void returnProxy(Proxy proxy, Page page, Task task);

    Proxy getProxy(Task task);
    
}
