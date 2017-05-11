package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.spider.Task;
import crawler.web.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler extends DuplicateRemovedScheduler implements IMonitorableScheduler {

    private BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        queue.add(request);
    }

    @Override
    public Request poll(Task task) {
        return queue.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }
}
