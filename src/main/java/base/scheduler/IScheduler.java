package base.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import base.web.Request;
import base.Task;

public interface IScheduler {

    // fetch
    void push(Request request, Task task);

    // crawl
    Request poll(Task task);

}
