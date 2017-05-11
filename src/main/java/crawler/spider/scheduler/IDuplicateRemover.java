package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.spider.Task;
import crawler.web.Request;

public interface IDuplicateRemover {

    boolean isDuplicate(Request request, Task task);

    void resetDuplicateCheck(Task task);

    int getTotalRequestsCount(Task task);

}
