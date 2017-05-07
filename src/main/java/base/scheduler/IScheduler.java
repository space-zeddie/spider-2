package base.scheduler;

import base.web.Request;
import base.Task;

/**
 * Created by matvii on 11.04.17.
 */
public interface IScheduler {

    // fetch
    void push(Request request, Task task);

    // crawl
    Request poll(Task task);

}
