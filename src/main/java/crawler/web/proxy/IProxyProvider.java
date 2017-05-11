package crawler.web.proxy;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.Task;
import crawler.web.Page;

public interface IProxyProvider {

    void returnProxy(Proxy proxy, Page page, Task task);

    Proxy getProxy(Task task);
    
}
