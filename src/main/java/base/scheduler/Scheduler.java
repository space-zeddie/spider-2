package base.scheduler;

import base.web.Request;
import base.Task;


/**
 * Scheduler is the part of url management.<br>
 * You can implement interface Scheduler to do:
 * manage urls to fetch
 * remove duplicate urls
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public interface Scheduler {

    // featch
    void push(Request request, Task task);

    // crawl
    Request poll(Task task);

}
