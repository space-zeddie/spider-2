package base.proxy;

import base.Task;
import base.web.Page;

public interface IProxyProvider {

    void returnProxy(Proxy proxy, Page page, Task task);

    Proxy getProxy(Task task);
    
}
