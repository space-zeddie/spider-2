package crawler.web.proxy;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.spider.Task;
import crawler.web.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProxyProvider  implements IProxyProvider {

    private final List<Proxy> proxies;

    private final AtomicInteger pointer;

    private ProxyProvider(List<Proxy> proxies, AtomicInteger pointer) {
        this.proxies = proxies;
        this.pointer = pointer;
    }

    public static ProxyProvider from(Proxy... proxies) {
        List<Proxy> proxiesTemp = new ArrayList<Proxy>(proxies.length);
        for (Proxy proxy : proxies) {
            proxiesTemp.add(proxy);
        }
        return new ProxyProvider(Collections.unmodifiableList(proxiesTemp), new AtomicInteger(-1));
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
        //TO DO??
    }

    @Override
    public Proxy getProxy(Task task) {
        return proxies.get(increaseForLoop());
    }

    private int increaseForLoop() {
        int p = pointer.incrementAndGet();
        int size = proxies.size();
        if (p < size) {
            return p;
        }
        while (!pointer.compareAndSet(p, p % size)) {
            p = pointer.get();
        }
        return p % size;
    }
}
