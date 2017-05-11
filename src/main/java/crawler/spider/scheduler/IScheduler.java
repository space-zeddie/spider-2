package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.web.Request;
import crawler.Task;

public interface IScheduler {

    // fetch
    void push(Request request, Task task);

    // crawl
    Request poll(Task task);

}
